package com.example.feedme.ui.components.home

import android.util.Log
import android.widget.Toast
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedme.FavoritesList
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel
import com.example.feedme.ui.components.viewModel.HomeViewModel
import javax.inject.Inject


class MainScreen @Inject constructor(private val viewModel: HomeViewModel, private val favoriteViewModel : FavoriteViewModel) {


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
                        onClick = {viewModel.onEventTrigger(EventTrigger.SearchEvent)
                            navController.navigate("accueil") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                        label = { Text("Accueil") },
                        unselectedContentColor = Color.White,


                        )
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == "parcourir",
                        onClick = { navController.navigate("parcourir") },
                        icon = {
                            Icon(
                                Icons.Default.ManageSearch,
                                contentDescription = "Parcourir"
                            )
                        },
                        label = { Text("Parcourir") },
                        unselectedContentColor = Color.White,
                    )
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == "favoris",
                        onClick = { favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent)
                                    navController.navigate("favoris") },
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
                    composable("parcourir") { ParcourirScreen() }
                    composable("favoris") { FavoriteScreen() }
                }
            }
        }
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun AccueilScreen(navigateToFavoriteList: (String) -> Unit) {
        var query = viewModel.query.value
        val page = viewModel.page.value
        val favorites : MutableState<MainState> = viewModel.favorite
        var recipes: MutableState<MainState> = viewModel.recipe


        //var currentPage: MutableState<Int> = remember { mutableStateOf(1) }
        val isNetworkAvailable = viewModel.isNetworkAvailable()

        if (isNetworkAvailable) {
            // afficher le contenu de l'interface
        } else {
            // afficher un toast pour informer l'utilisateur qu'il n'y a pas de connexion
            Toast.makeText(LocalContext.current, "Pas de connexion internet", Toast.LENGTH_SHORT)
                .show()
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
                    //            LazyRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent)
//                    .padding(horizontal = 16.dp, vertical = 8.dp)
//
//
//                //horizontalArrangement = Arrangement.spacedBy(16.dp),
//            ) {
//                itemsIndexed(items = recipes.value.data) { index, recipe ->
//                    RecipeCard(recipe,::test ,viewModel)
//                }
//            }
                    FavoritesList(
                        recipes = favorites.value.data,
                        viewModel::addOrDeleteToFavorite,
                        navigateToFavoriteList
                    )
                    Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
                }

                itemsIndexed(items = recipes.value.data) { index, recipe ->

                    RecipeCard(
                        recipe = recipe,
                        OnFavoriteClick = viewModel::addOrDeleteToFavorite
                    )
                    if ((index + 1) >= (page * 30) && !recipes.value.isLoading) {
                        viewModel.onEventTrigger(EventTrigger.NextPageEvent)
                    }
                }

            }

        }


    }


    @Composable
    fun ParcourirScreen() {
        Text(text = "Parcourir")
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun FavoriteScreen() {
        var query = favoriteViewModel.query.value
        val page = favoriteViewModel.page.value
        var favorites : MutableState<MainState> = favoriteViewModel.favorite


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
                query = query, onSearch = { favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent) },
                onQueryChange = favoriteViewModel::onQueryChange
            )

            Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))




            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
                }

                itemsIndexed(items = favorites.value.data) { index, recipe ->

                    RecipeCard(
                        recipe = recipe,
                        OnFavoriteClick = favoriteViewModel::addOrDeleteToFavorite
                    )
                    if ((index + 1) >= (page * 30) && !favorites.value.isLoading) {
                        favoriteViewModel.onEventTrigger(EventTrigger.NextPageEvent)
                    }
                }

            }

        }
    }

}