package com.example.feedme.ui.components.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.feedme.Categories.CategoriesScreen
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.favorite.FavoriteListScreen
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel
import com.example.feedme.ui.components.recipe.RecipeListScreen
import com.example.feedme.ui.components.recipeItem.RecipeDetails
import com.example.feedme.ui.components.recipeItem.RecipeDetailsViewModel
import com.example.feedme.ui.components.viewModel.HomeViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import javax.inject.Inject


class MainScreen @Inject constructor(
    private val viewModel: HomeViewModel,
    private val favoriteViewModel: FavoriteViewModel,
) {
    @OptIn(ExperimentalAnimationApi::class)

    @Composable
    fun MainContent() {
        val navController = rememberAnimatedNavController()
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
                AnimatedNavHost(navController, startDestination = "accueil") {
                    composable("accueil") {
                        RecipeListScreen(viewModel, navController)
                    }
                    composable("categories") {
                        CategoriesScreen(onClick = {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)

                        }, navController::navigate, viewModel)
                    }
                    composable("favoris") { FavoriteListScreen(favoriteViewModel, navController) }
                    composable("recipeDetails/{recipeId}",
                        arguments = listOf(navArgument("recipeId") { type = NavType.IntType }),
                        enterTransition = {
                            when (initialState.destination.route) {
                                "accueil" ->
                                    slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Up,
                                        animationSpec = tween(700)
                                    )
                                "favoris" ->
                                    slideIntoContainer(
                                        AnimatedContentScope.SlideDirection.Up,
                                        animationSpec = tween(700)
                                    )
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                "accueil" ->
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Down,
                                        animationSpec = tween(700)
                                    )
                                "favoris" ->
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Down,
                                        animationSpec = tween(700)
                                    )
                                else -> null
                            }
                        },
                        popExitTransition = {
                            when (targetState.destination.route) {
                                "accueil" ->
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Down,
                                        animationSpec = tween(700)
                                    )
                                "favoris" ->
                                    slideOutOfContainer(
                                        AnimatedContentScope.SlideDirection.Down,
                                        animationSpec = tween(700)
                                    )
                                else -> null
                            }
                        }

                    ) {
                        var recipeDetailsViewModel: RecipeDetailsViewModel = hiltViewModel()
                        RecipeDetails(
                            it.arguments?.getInt("recipeId"),
                            onBack = { navController.popBackStack() },
                            recipeDetailsViewModel
                        )
                    }
                }
            }
        }
    }
}