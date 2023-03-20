package com.example.feedme.ui.components.home

import com.example.feedme.domain.RecipeWithFavorite

data class MainState(
    val isLoading:Boolean=false,
    val data:List<RecipeWithFavorite> = emptyList(),
    val error:String=""
)