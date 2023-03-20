package com.example.feedme.ui.components

import com.example.feedme.domain.RecipeWithFavorite

data class MainState(
    val isLoading:Boolean=false,
    val data:List<RecipeWithFavorite> = emptyList(),
    val error:String=""
)