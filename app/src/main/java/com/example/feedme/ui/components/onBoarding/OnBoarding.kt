package com.example.feedme.ui.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.feedme.Onboarding.OnboardingScreen
import com.example.feedme.navigation.Screen
import com.example.feedme.ui.components.onBoarding.onboardingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(navigateToRecipeList: (String) -> Unit) {
    val PREFS_NAME = "OnboardPREF"
    val PREF_KEY_ONBOARDING_COMPLETE = "onboardingComplete"
    val prefs = LocalContext.current.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val onboardingComplete = prefs.getBoolean(PREF_KEY_ONBOARDING_COMPLETE, false)

    LaunchedEffect(onboardingComplete) {
        if (onboardingComplete) {
            navigateToRecipeList(Screen.RecipeList.route)

        }
    }

    OnboardingScreen(onboardingItems) {
        prefs.edit().putBoolean(PREF_KEY_ONBOARDING_COMPLETE, true).apply()
        navigateToRecipeList(Screen.RecipeList.route)

    }
}