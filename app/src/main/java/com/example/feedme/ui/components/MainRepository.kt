package com.example.feedme.ui.components

import com.example.feedme.network.RecipeService
import com.example.feedme.network.response.RecipeSearchResponse
import com.example.feedme.util.Constants
import com.example.feedme.util.Resource
import javax.inject.Inject


class MainRepository @Inject constructor(private val recipeService: RecipeService) {

    suspend fun getQueryItems(q: String, page: Int): Resource<RecipeSearchResponse> {
        return try {
            val result =
                recipeService.searchRecipes(query = q, token = Constants.AUTH_TOKEN, page = page)

            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }
}