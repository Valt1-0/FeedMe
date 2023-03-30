package com.example.feedme.Onboarding

import androidx.compose.ui.graphics.Color
import com.example.feedme.R
import com.example.feedme.ui.theme.Primary

data class OnboardingItem(
    val title: String,
    val desc: String,
    val backgroundColor: Color,
    val image: Int,
    val mainColor: Color = Primary,
)

val onboardingItems = listOf(
    OnboardingItem(
        title = "Bienvenue",
        desc = "Bienvenue sur Feed Me !",
        backgroundColor = Primary,
        image = R.drawable.rajesh
    ),
    OnboardingItem(
        title = "Recherche",
        desc = "Recherchez des recettes de cuisine en utilisant des ingrédients ou des mots-clés !",
        backgroundColor = Primary,
        image = R.drawable.valeria
    ),
    OnboardingItem(
        title = "Favoris",
        desc = "Sauvegardez vos recettes de cuisine préférées pour y accéder rapidement !",
        backgroundColor = Primary,
        image = R.drawable.jonathan
    )
)