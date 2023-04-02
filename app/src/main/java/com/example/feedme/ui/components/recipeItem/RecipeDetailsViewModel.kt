package com.example.feedme.ui.components.recipeItem

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.database.RecipeDao
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.favorite.EventTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val recipeDao: RecipeDao) : ViewModel() {

    val recipe: MutableState<RecipeWithFavorite?> = mutableStateOf(RecipeWithFavorite())
    var isLoading: MutableState<Boolean> = mutableStateOf(false)
    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    fun onTriggerEvent(event: EventTrigger) {
        viewModelScope.launch {
            try {
                when (event) {
                    is EventTrigger.GetRecipeEvent -> {
                        if (recipe.value?.id == 0) {
                            getRecipe(event.id)
                        }
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                Log.e("Debug", "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getRecipe(id: Int) = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                val resultDB = recipeDao.searchById(id)
                isLoading.value = false
                recipe.value = resultDB
            }

        } catch (e: Exception) {
            println("ERROR " + e.message.toString())
        }
    }
}