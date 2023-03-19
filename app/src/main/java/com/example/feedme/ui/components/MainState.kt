package com.example.feedme.ui.components

import com.example.feedme.domain.Recipe

data class MainState(
    val isLoading:Boolean=false,
    val data:List<Recipe> = emptyList(),
    val error:String=""
)