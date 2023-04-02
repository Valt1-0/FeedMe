package com.example.feedme.ui.components.favorite.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.action.FavoriteAction
import com.example.feedme.database.FavoriteDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.home.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao,
) : ViewModel() {
    var favorite: MutableState<MainState> = mutableStateOf(MainState())
    val query: MutableState<String> = mutableStateOf("")
    val page: MutableState<Int> = mutableStateOf(1)
    val pageSize: MutableState<Int> = mutableStateOf(30)


    private fun search() = viewModelScope.launch {
        favorite.value = MainState(isLoading = true, data = favorite.value.data)

        try {
            val resultDb = FavoriteAction(
                favoriteDao,
                query.value,
                page.value,
                pageSize.value
            ).searchFavorites()

            if (resultDb.isNullOrEmpty()) {
                favorite.value = MainState(error = "NoResult")
            } else {
                appendSearch(resultDb)
            }

        } catch (e: Exception) {
            favorite.value = MainState(error = "Something went wrong")
        }
    }

    /* TRIGGER */
    fun onEventTrigger(eventTrigger: EventTrigger) {
        try {
            when (eventTrigger) {
                is EventTrigger.SearchEvent -> {
                    newSearch()
                }
                is EventTrigger.NextPageEvent -> {
                    nextPage()
                }
                else -> {}
            }
        } catch (ex: Exception) {
            Log.d("TAG", "Erreur on EventTrigger Favorite")
        }
    }

    fun onQueryChange(query: String) {
        setQuery(query)
    }

    fun addOrDeleteToFavorite(id: Int, status: Boolean) = viewModelScope.launch {
        favorite.value =
            MainState(data = favorite.value.data, isLoading = true, error = favorite.value.error)
        val recipeFavorite = RecipeFavorite(id)

        if (!status) {
            FavoriteAction(favoriteDao, query.value, 1, pageSize.value).deleteFavorite(
                recipeFavorite
            )
            val favoriteListWithRemove = favorite.value.data.filter { it.id != id }

            if (favoriteListWithRemove.size != favorite.value.data.size) {
                favorite.value = MainState(
                    data = favoriteListWithRemove,
                    isLoading = false,
                    error = favorite.value.error
                )
            }
        }
    }

    private fun nextPage() {
        setPage(page.value + 1)
        if (page.value > 1) {
            viewModelScope.launch {
                try {
                    val resultDb = FavoriteAction(
                        favoriteDao,
                        query.value,
                        page.value,
                        pageSize.value
                    ).searchFavorites()

                    if (resultDb.isNullOrEmpty())
                        favorite.value = MainState(error = "NoResult", isLoading = false)
                    else
                        appendSearch(resultDb)
                } catch (e: Exception) {
                    favorite.value = MainState(error = "Something went wrong")
                }
            }
        }
    }

    private fun setQuery(query: String) {
        this.query.value = query
    }

    private fun appendSearch(favorites: List<RecipeWithFavorite>) {
        var current = ArrayList<RecipeWithFavorite>(favorite.value.data)
        current.addAll(favorites)
        this.favorite.value = MainState(data = current.toList(), isLoading = false)
    }

    private fun newSearch() {
        favorite.value = MainState(isLoading = true)
        setPage(1)
        search()
    }

    private fun setPage(page: Int) {
        this.page.value = page
    }
}