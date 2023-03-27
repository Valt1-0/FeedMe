package com.example.feedme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedme.SplashScreen.Snackbar
import com.example.feedme.navigation.Screen
import com.example.feedme.ui.components.OnBoarding
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.components.favorite.viewModel.FavoriteViewModel
import com.example.feedme.ui.components.home.MainScreen
import com.example.feedme.ui.components.viewModel.HomeViewModel
import com.example.feedme.ui.theme.FeedMeTheme
import com.example.feedme.ui.theme.MainTheme
import com.example.feedme.ui.theme.OnBoardingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay


@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            MainTheme {
                MyApp {
                    MainActivityUI()
                }
            }
        }

    }


    @Composable
    fun MainActivityUI() {
        val navController = rememberNavController()
        val viewModel: HomeViewModel = viewModel()


        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(Screen.SplashScreen.route) {
                FeedMeTheme {
                    SplashScreen()
                    if (viewModel.isNetworkAvailable()) {
                        LaunchedEffect(key1 = Unit)
                        {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)
                            delay(5000)
                            navController.navigate(Screen.OnBoarding.route)
                        }
                    } else {
                        Snackbar(::reloadActivity, {
                            viewModel.onEventTrigger(EventTrigger.SearchEvent)
                            navController.navigate(Screen.OnBoarding.route)
                        }, viewModel)


                    }

                }
            }
            composable(Screen.RecipeList.route) {
                val factory = HiltViewModelFactory(LocalContext.current, it)
                val favoriteViewModel: FavoriteViewModel = viewModel(factory = factory)
                //   val favoriteviewModel: FavoriteViewModel = viewModel()
                MainTheme() {
                    MainScreen(viewModel, favoriteViewModel).MainContent()
                }
            }

            composable(Screen.OnBoarding.route) {
                OnBoardingTheme() {
                    OnBoarding(navigateToRecipeList = navController::navigate)
                }
            }
            // Ajouter d'autres destinations de navigation ici
        }

    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        content()
    }

    private fun reloadActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


//    @Composable
//    fun MyScreen(viewModel: HomeViewModel, context: Context) {
//      //  val searchResults by viewModel.observeSearchMeal().observeAsState(emptyList())
//
//        if (searchResults.isEmpty()) {
//            Toast.makeText(context, "No such a meal", Toast.LENGTH_SHORT).show()
//        } else {
//            RecipeListScreen(searchResults, ::onChangeScrollPosition)
//        }
//    }
//    @Composable
//    fun RecipeList(recipes: List<Recipe>) {
//        val query = remember { mutableStateOf("") }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp, bottom = 10.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                TextField(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 15.dp),
//                    value = query.value,
//                    onValueChange = { query.value = it },
//                    placeholder = { Text(text = "Recettes ...") },
//                    singleLine = true,
//                    leadingIcon = {
//                        Icon(
//                            Icons.Default.Search,
//                            contentDescription = "Recherche",
//                            modifier = Modifier.size(35.dp),
//                            tint = Color.Black
//                        )
//                    },
//                    trailingIcon = {
//                        IconButton(onClick = { query.value = "" }) {
//                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Black)
//                        }
//                    },
//                    shape = InputShape.large,
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = MaterialTheme.colors.primaryVariant,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Done,
//                    ),
//                )
//            }
//        }
//
//        LazyColumn {
//            itemsIndexed(items = recipes) { index, recipe ->
//                if ((index + 1) >= (recipes.size)) {
//                    onChangeScrollPosition(index / 30 + 1)
//                }
//                Card(
//                    modifier = Modifier.padding(16.dp),
//                    elevation = 4.dp
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Text(text = recipe.title, style = MaterialTheme.typography.h5)
//                        Text(text = recipe.publisher, style = MaterialTheme.typography.subtitle1)
//                    }
//                }
//            }
//        }
//    }}
//
//    @Composable
//    fun RecipeListScreen() {
//        val searchResults by viewModel.observeSearchMeal().collectAsState(emptyList())
//    println("searchResults"+searchResults?.size)
//        val currentRecipes by rememberUpdatedState(searchResults)
//
//
//         if (currentRecipes?.isNotEmpty() == true)
//             RecipeList(currentRecipes.orEmpty())
//
//
////            searchResults?.let {
////                RecipeList(it)
////            }
//
//
//    }
//    @Composable
//    private fun onChangeScrollPosition(index: Int) {
//        println("Scroll")
//        LaunchedEffect(Int) {
//            viewModel.SearchRecipe("beef", 2,this@MainActivity)
//        }
//
//    }
}
