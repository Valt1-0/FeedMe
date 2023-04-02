package com.example.feedme.ui.components.recipe

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feedme.Categories.CategoriesCard
import com.example.feedme.FavoritesList
import com.example.feedme.R
import com.example.feedme.ui.blankScreen
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.categories.categoriesItem
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.home.MainState
import com.example.feedme.ui.components.viewModel.HomeViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecipeListScreen(viewModel: HomeViewModel, navController: NavController) {
    var query = viewModel.query.value
    val page = viewModel.page.value
    val favorites: MutableState<MainState> = viewModel.favorite
    var recipes: MutableState<MainState> = viewModel.recipe
    val isNetworkAvailable = viewModel.isNetworkAvailable()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(isNetworkAvailable)
    {
        if (!isNetworkAvailable)
        // afficher un toast pour informer l'utilisateur qu'il n'y a pas de connexion
            Toast.makeText(context, "Pas de connexion internet", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        if (recipes.value.isLoading) {
            Log.d("TAG", "MainContent: in the loading")
            LinearProgressIndicator(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            )
        }

        LaunchedEffect(favorites.value.data) {
            if (favorites.value.data.size == 1) {
                if (listState.firstVisibleItemIndex < 2)
                    listState.scrollToItem(0)
            }
        }

        SearchBar(
            query = query, onSearch = { viewModel.onEventTrigger(EventTrigger.SearchEvent) },
            onQueryChange = viewModel::onQueryChange
        )

        Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))

        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
            item {
                LazyRow(Modifier.fillMaxWidth()) {
                    items(categoriesItem) { categorie ->
                        CategoriesCard(
                            categorie = categorie,
                            onClick = { viewModel.onEventTrigger(EventTrigger.SearchEvent) },
                            viewModel = viewModel,
                            navigateToFavoriteList = navController::navigate,
                            modifier = Modifier
                                .size(160.dp)
                                .scale(0.85f)
                        )
                    }
                }

                Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))

                FavoritesList(
                    recipes = favorites.value.data,
                    viewModel::addOrDeleteToFavorite,
                    navController::navigate
                )

                if (recipes.value.isLoading && recipes.value.data.isEmpty()) {
                    repeat(8) {
                        CardWithShimmerEffect()
                    }
                } else if (!recipes.value.isLoading && recipes.value.data.isEmpty()) {
                    blankScreen(R.drawable.nosearchresult, "Aucune recette trouvée.")
                }

            }


            if (recipes.value.data.isNotEmpty()) {
                itemsIndexed(items = recipes.value.data) { index, recipe ->

                    RecipeCard(
                        recipe = recipe,
                        OnFavoriteClick = { id, status ->
                            viewModel.addOrDeleteToFavorite(
                                id,
                                status,
                            )
                        },
                        NavigateToRecipeDetails = navController::navigate,
                        modifier = Modifier
                    )
                    if ((index + 1) >= (page * 30) && !recipes.value.isLoading) {
                        viewModel.onEventTrigger(EventTrigger.NextPageEvent)
                    }
                }
            }
        }
    }
}
