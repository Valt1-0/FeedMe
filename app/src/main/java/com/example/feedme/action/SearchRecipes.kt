package com.example.feedme.action

import com.example.feedme.database.RecipeDao
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.MainRepository
import com.example.feedme.ui.components.home.MainState
import com.example.feedme.util.Constants
import com.example.feedme.util.RecipeDtoMapper
import com.example.feedme.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRecipes(
    private val query: String,
    private val page: Int,
    private val isConnectedToInternet: Boolean,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val mainRepository: MainRepository,
    private val recipeDao: RecipeDao,
) {


    suspend fun Search(): MainState {
        var recipe = MainState()

        if (isConnectedToInternet) {
            try {
                println("query : " + query + "page " + page.toString())
                val result = mainRepository.getQueryItems(query, page)
                println("result " + (result is Resource.Error).toString() + " ELSE " + (result is Resource.Success).toString())
                when (result) {
                    is Resource.Error -> {
                        recipe = MainState(error = "Something went wrong")
                    }
                    is Resource.Success -> {
                        println(result.data?.results?.let {
                            recipeDtoMapper.toRecipeList(it).toString()
                        }.toString())
                        result.data?.results?.let {

                            withContext(Dispatchers.IO) {
                                //  recipeDao.deleteAllRecipes()
                                println("SIZE FETCH API " + recipeDtoMapper.toRecipeList(it).size)
                                recipeDao.insertRecipes(recipeDtoMapper.toRecipeList(it))

                                var dbResult = SearchFromDatabase()

                                println("current.size " + dbResult.size.toString())
                                recipe = MainState(data = dbResult.toList(), isLoading = false)
                            }
                        }
                    }
                    else -> {
                        recipe = MainState(error = "Something went wrong")
                    }
                }


            } catch (e: Exception) {
                println("error" + e.message.toString())
                recipe = MainState(error = "Something went wrong")
            }
        } else {
            withContext(Dispatchers.IO) {
                var dbResult = SearchFromDatabase()

                // println("current.size "+current.size.toString())
                recipe = MainState(data = dbResult.toList(), isLoading = false)
            }
        }


        return recipe

    }

    fun SearchFromDatabase(): List<RecipeWithFavorite> {

        //Recherche dans la base de données
        val dbResult = if (query.isBlank()) {
            recipeDao.searchRecipes(
                page = page,
                pageSize = Constants.RECIPE_PER_PAGE
            )
        } else {
            recipeDao.searchRecipesWithQuery(
                query = query,
                page = page,
                pageSize = Constants.RECIPE_PER_PAGE
            )
        }

        return dbResult

    }


}