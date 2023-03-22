package com.example.feedme.domain

import java.util.*

data class RecipeWithFavorite(
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int,
    val sourceUrl: String,
    val ingredients: String,
    val dateAdded: Date?,
    val dateUpdated: Date?,
    val favorite: Boolean,
)