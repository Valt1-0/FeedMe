package com.example.feedme.ui.components.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.action.FavoriteAction
import com.example.feedme.database.FavoriteDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.home.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao
) : ViewModel() {
    var favorite: MutableState<MainState> = mutableStateOf(MainState())

    fun search(query:String, page:Int) = viewModelScope.launch {
        favorite.value = MainState(isLoading = true, data = favorite.value.data)

        try{
            var current = ArrayList<RecipeWithFavorite>(favorite.value.data)

            println("recipe.value.data.size " + favorite.value.data.size.toString())
            if (page == 1) {
                current.removeAll(favorite.value.data.toSet())
            }

            val resultDb = FavoriteAction(favoriteDao).searchFavorites(query,page)

            if(!resultDb.isNullOrEmpty() )
            {
                MainState(error = "Aucun Favoris ?")
            }
            else
            {
                current.addAll(resultDb)
                favorite.value = MainState(data = current.toList(), isLoading = false)
            }

        }catch (e: Exception) {
            println("error" + e.message.toString())
            favorite.value = MainState(error = "Something went wrong")
        }

    }

    fun addToFavorite(id: Int) = viewModelScope.launch {

        val recipeFavorite = RecipeFavorite(id)
        favoriteDao.insert(recipeFavorite)
    }

}