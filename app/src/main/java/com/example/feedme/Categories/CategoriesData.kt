package com.example.feedme.Categories

import com.example.feedme.R


data class CategoriesItem(
    val name: String,
    val image: Int,
    val query: String,
)

val categoriesItem = listOf(
    CategoriesItem(
        name = "Burgers",
        image = R.drawable.burgers,
        query = "burger"
    ),
    CategoriesItem(
        name = "Sandwichs",
        image = R.drawable.sandwichs,
        query = "sandwich"
    ),
    CategoriesItem(
        name = "Tacos / Fajitas",
        image = R.drawable.tacos,
        query = "tacos"
    ),
    CategoriesItem(
        name = "Viandes",
        image = R.drawable.beef,
        query = "beef"
    ),
    CategoriesItem(
        name = "Pâtes",
        image = R.drawable.pates,
        query = "pasta"
    ),
    CategoriesItem(
        name = "Pizzas",
        image = R.drawable.pizzas,
        query = "pizza"
    ),
    CategoriesItem(
        name = "Sushis",
        image = R.drawable.sushis,
        query = "sushi"
    ),
    CategoriesItem(
        name = "Salades",
        image = R.drawable.salads,
        query = "salad"
    ),
    CategoriesItem(
        name = "Poissons",
        image = R.drawable.fishs,
        query = "fish"
    ),
    CategoriesItem(
        name = "Desserts",
        image = R.drawable.desserts,
        query = "dessert"
    ),
)