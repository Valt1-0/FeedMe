package com.example.feedme.Categories

import com.example.feedme.R


data class CategoriesItem(
    val name: String,
    val image: Int,
)

val categoriesItem = listOf(
    CategoriesItem(
        name = "Burgers",
        image = R.drawable.burgers
    ),
    CategoriesItem(
        name = "Sandwichs",
        image = R.drawable.sandwichs
    ),
    CategoriesItem(
        name = "Tacos / Fajitas",
        image = R.drawable.tacos
    ),
    CategoriesItem(
        name = "Viandes",
        image = R.drawable.beef
    ),
    CategoriesItem(
        name = "Pates",
        image = R.drawable.pates
    ),
    CategoriesItem(
        name = "Pizzas",
        image = R.drawable.pizzas
    ),
    CategoriesItem(
        name = "Sushis",
        image = R.drawable.sushis
    ),
    CategoriesItem(
        name = "Salades",
        image = R.drawable.salads
    ),
    CategoriesItem(
        name = "Poissons",
        image = R.drawable.fishs
    ),
    CategoriesItem(
        name = "Desserts",
        image = R.drawable.desserts
    ),
)