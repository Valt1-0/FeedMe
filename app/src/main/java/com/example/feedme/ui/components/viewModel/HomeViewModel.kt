package com.example.feedme.ui.components.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedme.action.FavoriteAction
import com.example.feedme.action.SearchRecipes
import com.example.feedme.database.FavoriteDao
import com.example.feedme.database.RecipeDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.network.CheckNetworkConnexion
import com.example.feedme.ui.components.MainRepository
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.home.MainState
import com.example.feedme.util.RecipeDtoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val recipeDao: RecipeDao,
    private val favoriteDao: FavoriteDao,
    private val CheckNetworkConnexion: CheckNetworkConnexion
) : ViewModel() {
    var recipeInDB : MutableState<Boolean> = mutableStateOf(false   )
    var recipe: MutableState<MainState> = mutableStateOf(MainState())
    val query: MutableState<String> = mutableStateOf("")
    //Valeur par défaut 1 (offset)
    val page:MutableState<Int> = mutableStateOf(1)

    fun isNetworkAvailable(): Boolean {
        return CheckNetworkConnexion.isConnectedToInternet()
    }

    private fun search()  = viewModelScope.launch {
        recipe.value = MainState(isLoading = true, data = recipe.value.data)
        try {
            println("recipe.value.data.size " + recipe.value.data.size.toString())


            var result = SearchRecipes(
                query.value,
                page.value,
                isNetworkAvailable(),
                recipeDtoMapper,
                mainRepository,
                recipeDao
            ).Search()


            if(!result.error.isNullOrBlank() )
            {
                println("RESULT ERROR ")
                MainState(error = result.error)
            }
            else
            {
                appendSearch(result.data)
               // current.addAll(result.data)
              //  recipe.value = MainState(data = current.toList(), isLoading = false)
            }
        }catch (e: Exception) {
            println("error" + e.message.toString())
            recipe.value = MainState(error = "Something went wrong")
        }
    }

    fun addOrDeleteToFavorite(id: Int,status: Boolean) = viewModelScope.launch {
        recipe.value = MainState(data = recipe.value.data, isLoading = true, error = recipe.value.error)
        val recipeFavorite = RecipeFavorite(id)

        if (status)
            favoriteDao.insert(recipeFavorite)
        else
            FavoriteAction(favoriteDao).deleteFavorite(recipeFavorite)

        recipe.value.data.find { it.id == id }?.favorite = status
        recipe.value = MainState(data = recipe.value.data, isLoading = false, error = recipe.value.error)

    }



     fun onEventTrigger(eventTrigger: EventTrigger)
    {
        try {
            when (eventTrigger) {
                is EventTrigger.SearchEvent -> {
                    newSearch()
                }
                is EventTrigger.NextPageEvent -> {
                    nextPage()
                }
            }
        }
        catch (ex : Exception){
            Log.d("TAG", "Erreur on EventTrigger Favorite")
        }
    }
    private fun nextPage() = viewModelScope.launch {
        recipe.value = MainState(isLoading = true, data = recipe.value.data)
        setPage(page.value + 1)
        if(page.value > 1) {
            viewModelScope.launch {
                try {
                    val resultDb = SearchRecipes(query= query.value,page=page.value, isConnectedToInternet = isNetworkAvailable(),recipeDtoMapper = recipeDtoMapper,mainRepository= mainRepository,recipeDao=recipeDao ).Search()

                    if (resultDb.data.isNullOrEmpty())
                        if (recipe.value.data.isEmpty())
                            MainState(error = "Aucun résultat")
                        else
                            recipe.value = MainState(data = recipe.value.data, isLoading = false)
                    else
                        appendSearch(resultDb.data)
                }catch (e: Exception) {
                    println("error" + e.message.toString())
                    recipe.value = MainState(error = "Something went wrong",isLoading = false)
                }
            }
        }
    }

    private fun newSearch() = viewModelScope.launch  {
        recipe.value = MainState(isLoading = true)
        setPage(1)
        search()
    }


     fun onQueryChange(query:String) {
        setQuery(query)
    }
    private fun setQuery(query: String) {
        this.query.value = query
    }
    private fun setPage(page : Int){
        this.page.value = page
    }

   private fun appendSearch(recipes : List<RecipeWithFavorite>) {
       var current = ArrayList<RecipeWithFavorite>(recipe.value.data)
       println("RECIPE SIZE  : / "+ recipes.size.toString())
       if (recipes.isNotEmpty())
       {
           current.addAll(recipes)
       }

        this.recipe.value = MainState(data = current.toList(), isLoading = false)
    }


    fun recipeInDB()  = viewModelScope.launch  {
        recipeInDB.value = recipeDao.recipeInDB()
    }
    fun searchRecipe(q: String, page: Int) = viewModelScope.launch {

      //  search(q,page)
//
//        recipe.value = MainState(isLoading = true, data = recipe.value.data)
//
//        try {
//            println("query : " + q + "page " + page.toString())
//            val result = mainRepository.getQueryItems(q, page)
//            println("result " + (result is Resource.Error).toString() + " ELSE " + (result is Resource.Success).toString())
//            when (result) {
//                is Resource.Error -> {
//                    recipe.value = MainState(error = "Something went wrong")
//                }
//                is Resource.Success -> {
//                    println(result.data?.results?.let {
//                        recipeDtoMapper.toRecipeList(it).toString()
//                    }.toString())
//                    result.data?.results?.let {
//                        var current = ArrayList<RecipeWithFavorite>(recipe.value.data)
//
//                        println("recipe.value.data.size " + recipe.value.data.size.toString())
//                        if (page == 1) {
//                            current.removeAll(recipe.value.data)
//                        }
//
//
//                        println("id" + recipeDtoMapper.toRecipeList(it)[0].id.toString())
//                        //Insertion dans la base de données
//                        withContext(Dispatchers.IO) {
//                            //  recipeDao.deleteAllRecipes()
//                            recipeDao.insertRecipes(recipeDtoMapper.toRecipeList(it))
//
//
//                            //Recherche dans la base de données
//                            val dbResult = if (q.isBlank()) {
//                                recipeDao.searchRecipesWithQuery(
//                                    query = q,
//                                    page = page,
//                                    pageSize = RECIPE_PER_PAGE
//                                )
//                            } else {
//                                recipeDao.searchRecipesWithQuery(
//                                    query = q,
//                                    page = page,
//                                    pageSize = RECIPE_PER_PAGE
//                                )
//                            }
//
//                            println("Size db : " + dbResult.size.toString())
//                            current.addAll(dbResult)
//                            // println("current.size "+current.size.toString())
//                            recipe.value = MainState(data = current.toList(), isLoading = false)
//                        }
//                    }
//                }
//                else -> {}
//            }
//        } catch (e: Exception) {
//            println("error" + e.message.toString())
//            recipe.value = MainState(error = "Something went wrong")
//        }


    }







}