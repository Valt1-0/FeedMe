package com.example.feedme.ui.components.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.database.FavoriteDao
import com.example.feedme.database.RecipeDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.network.CheckNetworkConnexion
import com.example.feedme.ui.components.MainRepository
import com.example.feedme.ui.components.home.MainState
import com.example.feedme.util.Constants.RECIPE_PER_PAGE
import com.example.feedme.util.RecipeDtoMapper
import com.example.feedme.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val recipeDao: RecipeDao,
    private val favoriteDao: FavoriteDao,
    private val CheckNetworkConnexion: CheckNetworkConnexion
) : ViewModel() {
    var recipe: MutableState<MainState> = mutableStateOf(MainState())


    fun isNetworkAvailable(): Boolean {
        return CheckNetworkConnexion.isConnectedToInternet()
    }

    fun searchRecipe(q: String, page: Int) = viewModelScope.launch {
        recipe.value = MainState(isLoading = true, data = recipe.value.data)

        try {
            println("query : " + q + "page " + page.toString())
            val result = mainRepository.getQueryItems(q, page)
            println("result " + (result is Resource.Error).toString() + " ELSE " + (result is Resource.Success).toString())
            when (result) {
                is Resource.Error -> {
                    recipe.value = MainState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    println(result.data?.results?.let {
                        recipeDtoMapper.toRecipeList(it).toString()
                    }.toString())
                    result.data?.results?.let {
                        var current = ArrayList<RecipeWithFavorite>(recipe.value.data)

                        println("recipe.value.data.size " + recipe.value.data.size.toString())
                        if (page == 1) {
                            current.removeAll(recipe.value.data)
                        }


                        println("id" + recipeDtoMapper.toRecipeList(it)[0].id.toString())
                        //Insertion dans la base de données
                        withContext(Dispatchers.IO) {
                            //  recipeDao.deleteAllRecipes()
                            recipeDao.insertRecipes(recipeDtoMapper.toRecipeList(it))


                            //Recherche dans la base de données
                            val dbResult = if (q.isBlank()) {
                                recipeDao.searchRecipes(
                                    query = q,
                                    page = page,
                                    pageSize = RECIPE_PER_PAGE
                                )
                            } else {
                                recipeDao.searchRecipes(
                                    query = q,
                                    page = page,
                                    pageSize = RECIPE_PER_PAGE
                                )
                            }

                            println("Size db : " + dbResult.size.toString())
                            current.addAll(dbResult)
                            // println("current.size "+current.size.toString())
                            recipe.value = MainState(data = current.toList(), isLoading = false)
                        }
                    }
                }
                else -> {}
            }
        } catch (e: Exception) {
            println("error" + e.message.toString())
            recipe.value = MainState(error = "Something went wrong")
        }


    }

    fun addToFavorite(id: Int) = viewModelScope.launch {

        val recipeFavorite = RecipeFavorite(id)
        favoriteDao.insert(recipeFavorite)
    }



//    fun SearchRecipe(search:String,page:Int, context: MainActivity)
//    {
//        println("Start SearchRecipe")
//        RetrofitInstance.retrofit.searchRecipes(authToken,page,search).enqueue(object : Callback<RecipeSearchResponse>{
//            override fun onResponse(
//                call: Call<RecipeSearchResponse>,
//                response: Response<RecipeSearchResponse>
//            ) {
//
//
//
//                if(response.isSuccessful)
//                {
//                    println("OKKAYY")
//                    val recipelist = response.body()?.recipes
//                    recipelist?.let {
//                     //   SearchRecipeLiveData.postValue(it)
//                        println("FETCHAPI SIZE" + recipelist.size )
//
//                        viewModelScope.launch {
//
//                         var test2 =   recipeDtoMapper.toRecipeList(it)
//                            val recipeDao = appDatabase.recipeDao()
//                            recipeDao.insertRecipes(test2)
//
//                            repository = Repository(recipeDao)
//
//
//                            // Get recipes from database
//
//                            val test = repository.search(search, page)
//
//                            println("Size DB " + test.size.toString())
//
//                            allRecipes.addAll(test)
//                            _searchRecipeLiveData.value = allRecipes
//                         //   _searchRecipeLiveData.postValue(allRecipes)
//
//                            println("SearchRecipeLiveData size "+_searchRecipeLiveData.value?.size.toString())
//                        }.isCompleted
//
//
//
//
//                    }
//                }
//                else{
//                    println("FAILED SEARCH  //§§ ")
//                    Toast.makeText(context,"You connect may be error please try again",
//                        Toast.LENGTH_SHORT).show()               }
//            }
//
//            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
//                println(t.message.toString())
//                Toast.makeText(context,t.message.toString(),Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }
//    fun observeSearchMeal() : Flow<List<Recipe>?> {
//
//        return liveData {
//            val recipes = _searchRecipeLiveData.value
//            emit(recipes)
//        }.asFlow()
//     //   return _searchRecipeLiveData
//    }


}