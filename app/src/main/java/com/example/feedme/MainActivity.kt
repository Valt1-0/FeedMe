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
}
