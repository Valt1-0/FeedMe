package com.example.feedme.network

import com.example.feedme.network.model.RecipeDto
import com.example.feedme.network.response.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {
    @GET("search")
    suspend fun searchRecipes(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String,
    ): RecipeSearchResponse

    @GET("get")
    suspend fun getRecipeById(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): RecipeDto

}