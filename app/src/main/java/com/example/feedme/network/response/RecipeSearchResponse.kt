package com.example.feedme.network.response

import com.example.feedme.network.model.RecipeDto

data class RecipeSearchResponse(

    val count: Int,


    val results: List<RecipeDto>,
)
