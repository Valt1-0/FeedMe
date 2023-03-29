package com.example.feedme.ui.components.recipeItem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.database.RecipeDao
import com.example.feedme.domain.RecipeWithFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor (private val recipeDao: RecipeDao) : ViewModel() {

    val recipe : MutableState<RecipeWithFavorite> = mutableStateOf( RecipeWithFavorite())



    private fun search(id : Int) = viewModelScope.launch {
    try {
       val resultDB = recipeDao.searchById(id)
        recipe.value = resultDB

    } catch ( e: Exception ) {

    }
    }




}