package com.example.feedme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedme.SplashScreen.Snackbar
import com.example.feedme.navigation.Screen
import com.example.feedme.ui.components.OnBoarding
import com.example.feedme.ui.components.home.MainContent
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
//        val appDatabase = AppDatabase.getInstance(this)
//        val homeMVVMFactory = HomeMVVMFactory(appDatabase)
//        viewModel = ViewModelProvider(
//            this,
//            homeMVVMFactory
//        ).get(HomeViewModel(AppDatabase.getInstance(this@MainActivity))::class.java)
//        viewModel.SearchRecipe("beef", 1, this@MainActivity)

        setContent {

            MainTheme  {
                // A surface container using the 'background' color from the theme

                    MyApp {
//                        val isLoading = remember { mutableStateOf(true) }
//                        val isFirst = remember { mutableStateOf(true) }
//                        val coroutineScope = rememberCoroutineScope()
//                        if (isLoading.value && isFirst.value)
//                        {
//                            SplashScreen()
//                            isFirst.value = false
//                        LaunchedEffect(key1 = Unit)
//                        {
//                            coroutineScope.launch(Dispatchers.Main) {
//                                delay(5000)
//                                isLoading.value = false
//                            }
//                        }}
//                        if (isLoading.value) {
//                            // Affiche un splashscreen
//                            Surface(color = Color.Black) {
//                                // Ajoutez ici votre logo ou une image de chargement
//                            }
//                        } else {
//                            MainContent()
//                        }
                        MainActivityUI()
                    }
//                    viewModel.observeSearchMeal().observe(this, Observer<List<Recipe>> { t ->
//                        if (t == null) {
//                            Toast.makeText(this, "No such a meal", Toast.LENGTH_SHORT).show()
//                        } else {
//                            MyScreen(viewModel,this)
//                        }
//                    })
                //    val searchResults by viewModel.observeSearchMeal().observeAsState(emptyList())
                   // RecipeListScreen()
                    //   val recipesState by viewModel.observeSearchMeal().collectAsState(initial = emptyList())
                    //  val recipes: List<Recipe> by  viewModel.SearchRecipeLiveData.observeAsState(initial = emptyList())


                    //  RecipeListScreen(viewModel.SearchRecipeLiveData.observeAsState(initial = emptyList()).value, ::onChangeScrollPosition)


            }
        }

    }

    @Composable
    fun MainActivityUI() {
        val navController = rememberNavController()
        val viewModel: HomeViewModel = viewModel()
//        LaunchedEffect(key1 = true) {
//            delay(5000)
//            navController.navigate(Screen.OnBarding.route)
//        }



        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(Screen.SplashScreen.route) {
//                val factory = HiltViewModelFactory(LocalContext.current, it)
//                val viewModel: HomeViewModel = viewModel(factory = factory)
                FeedMeTheme {
                    SplashScreen(viewModel)
                    if (viewModel.isNetworkAvailable()) {
                        val coroutineScope = rememberCoroutineScope()
                        LaunchedEffect(key1 = Unit)
                        {
                            viewModel.searchRecipe("beef",1)
                            delay(5000)
                            navController.navigate(Screen.OnBarding.route)
                        }
                    } else {
                        Snackbar { reloadActivity() }
                    }

                }
            }
            composable(Screen.RecipeList.route) {
//                val factory = HiltViewModelFactory(LocalContext.current, it)
//                val viewModel: HomeViewModel = viewModel(factory = factory)
                    MainTheme() {
                        MainContent(viewModel = viewModel)
                    }


            }
            composable(Screen.OnBarding.route) {
                OnBoardingTheme() {
                    OnBoarding(navigateToRecipeList = navController::navigate)
                }
            }
            // Ajouter d'autres destinations de navigation ici
        }

    }
    @Composable
    fun MyApp(content: @Composable ()->Unit) {
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
