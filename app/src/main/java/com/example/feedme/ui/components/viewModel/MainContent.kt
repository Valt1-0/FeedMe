package com.example.feedme.ui.components.viewModel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar


@OptIn(ExperimentalFoundationApi::class)

@Composable
fun MainContent(viewModel: HomeViewModel = hiltViewModel()) {

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
fun AccueilScreen(viewModel:HomeViewModel) {
    val query: MutableState<String> = remember { mutableStateOf("beef") }
    val result = viewModel.recipe.value
    var currentPage : MutableState<Int> = remember { mutableStateOf(1) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchBar(onSearch = ::onQueryChanged, viewModel = viewModel )

        Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
        LazyColumn {
            itemsIndexed(items = viewModel.recipe.value.data) { index, recipe ->

                RecipeCard(recipe)
                if ((index + 1) >= (currentPage.value * 30) && !viewModel.recipe.value.isLoading) {
                    currentPage.value = currentPage.value + 1
                    viewModel.searchRecipe(query.value, currentPage.value)
                }
            }

        }
    }


}

fun onQueryChanged(query: String,viewModel : HomeViewModel)
{

    viewModel.searchRecipe(query,1)
}
@Composable
fun ParcourirScreen() {
    Text(text = "Parcourir")
}

