package com.example.feedme.ui.components.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedme.FavoritesList
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.viewModel.HomeViewModel


@OptIn(ExperimentalFoundationApi::class)

@Composable
fun MainContent(viewModel: HomeViewModel) {
//println("****** *** **** Size : " + viewModel.recipe.value.data.size)
    // viewModel.searchRecipe("beef",1)
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    selected = navController.currentDestination?.route == "accueil",
                    onClick = { navController.navigate("accueil") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                    label = { Text("Accueil") },
                    selectedContentColor = Color.Gray,
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
                    selectedContentColor = Color.Gray,
                    unselectedContentColor = Color.White,
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController, startDestination = "accueil") {
                composable("accueil") { AccueilScreen(viewModel) }
                composable("parcourir") { ParcourirScreen() }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AccueilScreen(viewModel: HomeViewModel) {
    val query: MutableState<String> = remember { mutableStateOf("beef") }
    val recipes: MutableState<MainState> = viewModel.recipe
    var currentPage: MutableState<Int> = remember { mutableStateOf(1) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (recipes.value.isLoading) {
            Log.d("TAG", "MainContent: in the loading")

            LinearProgressIndicator(Modifier.fillMaxWidth(), color = Color.Black)

        }


        SearchBar(onSearch = ::onQueryChanged, viewModel = viewModel)

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
               FavoritesList(recipes = recipes.value.data, viewModel = viewModel)
               Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
            }

            itemsIndexed(items = recipes.value.data) { index, recipe ->

                RecipeCard(
                    recipe = recipe,
                    OnFavoriteClick = ::onFavoriteClick,
                    viewModel = viewModel
                )
                if ((index + 1) >= (currentPage.value * 30) && !recipes.value.isLoading) {
                    currentPage.value = currentPage.value + 1
                    viewModel.searchRecipe(query.value, currentPage.value)
                }
            }

        }

    }


}

fun onFavoriteClick(id: Int, viewModel: HomeViewModel) {
    viewModel.addToFavorite(id)
}

fun onQueryChanged(query: String, viewModel: HomeViewModel) {

    viewModel.searchRecipe(query, 1)
}

@Composable
fun ParcourirScreen() {
    Text(text = "Parcourir")
}

