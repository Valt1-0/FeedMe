package com.example.feedme.navigation

sealed class Screen(
    val route: String,
) {
    object SplashScreen : Screen("splashScreen")

    object RecipeList : Screen("recipeList")

    object OnBoarding : Screen("onBoarding")
}