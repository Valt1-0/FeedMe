package com.example.feedme.ui.components.favorite.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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

const val STATE_KEY_QUERY_FAVORITE = "favorite.state.key.query"
const val STATE_KEY_PAGE_FAVORITE = "favorite.state.key.pageNumber"

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var favorite: MutableState<MainState> = mutableStateOf(MainState())
    val query: MutableState<String> = mutableStateOf("")
    //Valeur par défaut 1 (offset)
    val page:MutableState<Int> = mutableStateOf(1)
    val pageSize:MutableState<Int> = mutableStateOf(30)

    private fun search() = viewModelScope.launch {
        favorite.value = MainState(isLoading = true, data = favorite.value.data)

        try{


            val resultDb = FavoriteAction(favoriteDao,query.value,page.value,pageSize.value).searchFavorites()

            if(!resultDb.isNullOrEmpty() )
            {
                MainState(error = "Aucun Favoris ?")
            }
            else
            {
                appendSearch(resultDb)
//                current.addAll(resultDb)
//                favorite.value = MainState(data = current.toList(), isLoading = false)
            }

        }catch (e: Exception) {
            println("error" + e.message.toString())
            favorite.value = MainState(error = "Something went wrong")
        }

    }

    /* TRIGGER */
    private fun onEventTrigger(eventTrigger: EventTrigger)
    {
        try {
            when (eventTrigger) {
                is EventTrigger.SearchEvent -> {
                    newSearch()
                }
                is EventTrigger.NextPageEvent -> {
                    nextPage()
                }
            }
        }
        catch (ex : Exception){
            Log.d("TAG", "Erreur on EventTrigger Favorite")
        }
    }

    private fun onQueryChange(query:String) {
        setQuery(query)
    }

    fun addToFavorite(id: Int) = viewModelScope.launch {

        val recipeFavorite = RecipeFavorite(id)
        favoriteDao.insert(recipeFavorite)

    }

    fun deleteFavorite(id: Int)= viewModelScope.launch {

        val recipeFavorite = RecipeFavorite(id)
        FavoriteAction(favoriteDao,query.value,page.value,pageSize.value).deleteFavorite(recipeFavorite)
    }

    /* *** */



    private fun nextPage() {
        setPage(page.value + 1)
        if(page.value > 1) {
            viewModelScope.launch {
                try {
                    val resultDb = FavoriteAction(favoriteDao,query.value,page.value,pageSize.value).searchFavorites()

                    if (!resultDb.isNullOrEmpty())
                        MainState(error = "Aucun Favoris ?")
                    else
                        appendSearch(resultDb)
                  }catch (e: Exception) {
                println("error" + e.message.toString())
                favorite.value = MainState(error = "Something went wrong")
            }
            }
        }
    }

    private fun setPageSize(pageSize: Int)
    {
        this.pageSize.value = pageSize
    }

    private fun setQuery(query: String) {
        this.query.value = query
    }


    private fun appendSearch(favorites : List<RecipeWithFavorite>) {
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