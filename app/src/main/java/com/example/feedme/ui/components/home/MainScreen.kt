package com.example.feedme.ui.components.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.feedme.Categories.CategoriesCard
import com.example.feedme.Categories.CategoriesScreen
import com.example.feedme.Categories.categoriesItem
import com.example.feedme.FavoriteCard
import com.example.feedme.FavoritesList
import com.example.feedme.R
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.blankScreen
import com.example.feedme.ui.components.RecipeCard
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel
import com.example.feedme.ui.components.recipe.CardWithShimmerEffect
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
    private val SaveMap = mutableMapOf<String, ScrollKeyParams>()

    private data class ScrollKeyParams(
        val value: Int,
    )


    @OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)

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
                        AccueilScreen(navController)
                    }
                    composable("categories") {
//                        val factory = HiltViewModelFactory(LocalContext.current, it)
//                        val viewModel: HomeViewModel = viewModel("HomeViewModel", factory)
                        CategoriesScreen(onClick = {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)

                        }, navController::navigate, viewModel)
                    }
                    composable("favoris") { FavoriteScreen(navController::navigate) }
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

    @OptIn(
        ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
        ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
    )
    @Composable
    fun AccueilScreen(navController: NavController) {
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
                            CardWithShimmerEffect(recipes.value.isLoading)
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
                                addOrDeleteFavorite(
                                    id,
                                    status,
                                    listState,
                                    favorites.value.data
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

    @Composable
    fun rememberForeverScrollState(
        key: String,
        initial: Int = 0,
    ): ScrollState {
        val scrollState = rememberSaveable(saver = ScrollState.Saver) {
            val scrollValue: Int = SaveMap[key]?.value ?: initial
            SaveMap[key] = ScrollKeyParams(scrollValue)
            return@rememberSaveable ScrollState(scrollValue)
        }
        DisposableEffect(Unit) {
            onDispose {
                SaveMap[key] = ScrollKeyParams(scrollState.value)
            }
        }
        return scrollState
    }

    private fun addOrDeleteFavorite(
        id: Int,
        status: Boolean,
        listState: LazyListState,
        favorites: List<RecipeWithFavorite>,
    ) {
        viewModel.addOrDeleteToFavorite(id, status)
        println("Scroll position : " + listState.firstVisibleItemScrollOffset)
        if (listState.firstVisibleItemIndex < 1000 && favorites.isEmpty()) {
            println("Scroll to index 0 ")

            SaveMap["history_screen"] = ScrollKeyParams(0)


        }
    }

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun FavoriteScreen(navigateToFavoriteList: (String) -> Unit) {
        var query = favoriteViewModel.query.value
        val page = favoriteViewModel.page.value
        var favorites =  favoriteViewModel.favorite


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colors.onPrimary)
        ) {

            if (favorites.value.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
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

                    val dismissState= rememberDismissState(DismissValue.Default)
                    var isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

                    if(isDismissed)
                    {
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
                                OnFavoriteClick = favoriteViewModel::addOrDeleteToFavorite,
                                NavigateToRecipeDetails = navigateToFavoriteList
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
}