package com.example.feedme.ui.components.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feedme.Categories.CategoriesScreen
import com.example.feedme.FavoriteCard
import com.example.feedme.FavoritesList
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel
import com.example.feedme.ui.components.recipe.CardWithShimmerEffect
import com.example.feedme.ui.components.recipeItem.RecipeDetails
import com.example.feedme.ui.components.viewModel.HomeViewModel
import javax.inject.Inject


class MainScreen @Inject constructor(
    private val viewModel: HomeViewModel,
    private val favoriteViewModel: FavoriteViewModel,
) {


    @OptIn(ExperimentalFoundationApi::class)

    @Composable
    fun MainContent() {
//println("****** *** **** Size : " + viewModel.recipe.value.data.size)
        // viewModel.searchRecipe("beef",1)
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == "accueil",
                        onClick = {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)
                            navController.navigate("accueil")
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                        label = { Text("Accueil") },
                        unselectedContentColor = Color.White,


                        )
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == "categories",
                        onClick = { navController.navigate("categories") },
                        icon = {
                            Icon(
                                Icons.Default.ManageSearch,
                                contentDescription = "Liste des Categories"
                            )
                        },
                        label = { Text("Parcourir") },
                        unselectedContentColor = Color.White,
                    )
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == "favoris",
                        onClick = {
                            favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent)
                            navController.navigate("favoris")
                        },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "favoris"
                            )
                        },
                        label = { Text("Favoris") },
                        unselectedContentColor = Color.White,
                    )
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = "accueil") {
                    composable("accueil") {
                        AccueilScreen(navController::navigate)
                    }
                    composable("categories") {
//                        val factory = HiltViewModelFactory(LocalContext.current, it)
//                        val viewModel: HomeViewModel = viewModel("HomeViewModel", factory)
                        CategoriesScreen(onClick = {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)

                        },navController::navigate, viewModel)
                    }
                    composable("favoris") { FavoriteScreen(navController::navigate) }
                    composable(
                        "recipeDetails/{recipeId}",
                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                    ) { RecipeDetails(it.arguments?.getInt("recipeId"),onBack = { navController.popBackStack() }) }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
        ExperimentalComposeUiApi::class
    )
    @Composable
    fun AccueilScreen(navigateToFavoriteList: (String) -> Unit) {
       // CategoryScreen(viewModel)
   //     RecipeListScreen(viewModel,navigateToFavoriteList)
        var query = viewModel.query.value
        val page = viewModel.page.value
        val favorites: MutableState<MainState> = viewModel.favorite
        var recipes: MutableState<MainState> = viewModel.recipe
        //var currentPage: MutableState<Int> = remember { mutableStateOf(1) }
        val isNetworkAvailable = viewModel.isNetworkAvailable()

        if (isNetworkAvailable) {
            // afficher le contenu de l'interface
        } else {
            // afficher un toast pour informer l'utilisateur qu'il n'y a pas de connexion
            Toast.makeText(LocalContext.current, "Pas de connexion internet", Toast.LENGTH_SHORT).show()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (recipes.value.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
                LinearProgressIndicator(
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary
                )
            }

            SearchBar(
                query = query, onSearch = { viewModel.onEventTrigger(EventTrigger.SearchEvent) },
                onQueryChange = viewModel::onQueryChange
            )

            Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    FavoritesList(
                        recipes = favorites.value.data,
                        viewModel::addOrDeleteToFavorite,
                        navigateToFavoriteList
                    )

                    if (recipes.value.isLoading && recipes.value.data.isEmpty()) {
                        repeat(8) {
                            CardWithShimmerEffect(recipes.value.isLoading)
                        }
                    }
                }

                if (recipes.value.data.isNotEmpty()) {
                    itemsIndexed(items = recipes.value.data) { index, recipe ->

                        RecipeCard(
                            recipe = recipe,
                            OnFavoriteClick = viewModel::addOrDeleteToFavorite,
                            NavigateToRecipeDetails = navigateToFavoriteList
                        )
                        if ((index + 1) >= (page * 30) && !recipes.value.isLoading) {
                            viewModel.onEventTrigger(EventTrigger.NextPageEvent)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun FavoriteScreen(navigateToFavoriteList: (String) -> Unit) {
        var query = favoriteViewModel.query.value
        val page = favoriteViewModel.page.value
        var favorites: MutableState<MainState> = favoriteViewModel.favorite


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (favorites.value.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
                LinearProgressIndicator(
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary
                )
            }

            SearchBar(
                query = query,
                onSearch = { favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent) },
                onQueryChange = favoriteViewModel::onQueryChange
            )

            Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = favorites.value.data) { index, recipe ->

                    FavoriteCard(
                        recipe = recipe,
                        OnFavoriteClick = favoriteViewModel::addOrDeleteToFavorite,
                        NavigateToRecipeDetails = navigateToFavoriteList
                    )
                    if ((index + 1) >= (page * 30) && !favorites.value.isLoading) {
                        favoriteViewModel.onEventTrigger(EventTrigger.NextPageEvent)
                    }
                }

            }

        }
    }

}