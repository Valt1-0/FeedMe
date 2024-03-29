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
                val result = mainRepository.getQueryItems(query, page)
                when (result) {
                    is Resource.Error -> {
                        recipe = MainState(error = "Something went wrong")
                    }
                    is Resource.Success -> {
                        result.data?.results?.let {
                            withContext(Dispatchers.IO) {
                                recipeDao.insertRecipes(recipeDtoMapper.toRecipeList(it))
                                var dbResult = SearchFromDatabase()

                                recipe = MainState(data = dbResult.toList(), isLoading = false)
                            }
                        }
                    }
                    else -> {
                        recipe = MainState(error = "Something went wrong")
                    }
                }
            } catch (e: Exception) {
                recipe = MainState(error = "Something went wrong")
            }
        } else {
            withContext(Dispatchers.IO) {
                var dbResult = SearchFromDatabase()
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