package com.example.feedme.ui.components.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feedme.FavoriteCard
import com.example.feedme.R
import com.example.feedme.ui.blankScreen
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun FavoriteListScreen(favoriteViewModel: FavoriteViewModel, navController: NavController) {
    var query = favoriteViewModel.query.value
    val page = favoriteViewModel.page.value
    var favorites = favoriteViewModel.favorite


    LaunchedEffect(Unit) {
        favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
    ) {

        if (favorites.value.isLoading) {
            LinearProgressIndicator(
                Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            )
        }

        if (favorites.value.data.isNotEmpty() || !query.isNullOrBlank()) {
            SearchBar(
                query = query,
                onSearch = { favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent) },
                onQueryChange = favoriteViewModel::onQueryChange
            )

            Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
        }
        if (!favorites.value.isLoading && favorites.value.data.isEmpty()) {
            if (!query.isNullOrBlank())
                blankScreen(R.drawable.nosearchresult, "Aucun favoris trouvé")
            else
                blankScreen(R.drawable.nofavorite, "Vous n'avez pas encore de favoris.")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items = favorites.value.data) { index, recipe ->

                val dismissState = rememberDismissState(DismissValue.Default)
                var isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

                if (isDismissed) {
                    LaunchedEffect(isDismissed) {
                        favoriteViewModel.addOrDeleteToFavorite(recipe.id, false)
                        dismissState.reset()
                    }
                }
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color(0xFFCC0000)
                            null -> Color.Transparent
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    },
                    dismissContent = {
                        FavoriteCard(
                            recipe = recipe,
                            NavigateToRecipeDetails = navController::navigate
                        )
                        if ((index + 1) >= (page * 30) && !favorites.value.isLoading) {
                            favoriteViewModel.onEventTrigger(EventTrigger.NextPageEvent)
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart)
                )
            }
        }
    }
}