package com.example.feedme.ui.components.recipe

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.feedme.FavoritesList
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.home.MainState
import com.example.feedme.ui.components.viewModel.HomeViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipeListScreen(viewModel: HomeViewModel,navigateToFavoriteList : (String) -> Unit) {
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

                if (recipes.value.isLoading && recipes.value.data.isEmpty()) {
                    repeat(8) {
                        CardWithShimmerEffect(recipes.value.isLoading)
                    }
                }
            }


            if (!recipes.value.isLoading) {
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