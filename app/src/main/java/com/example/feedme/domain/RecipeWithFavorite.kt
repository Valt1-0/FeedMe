package com.example.feedme.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RecipeWithFavorite(
    val id: Int = 0,
    val title: String = "",
    val publisher: String = "",
    val featuredImage: String = "",
    val rating: Int = 0,
    val sourceUrl: String = "",
    val ingredients: String = "",
    val dateAdded: Date? = null,
    val dateUpdated: Date? = null,
    var favorite: Boolean = false,
) : Parcelable