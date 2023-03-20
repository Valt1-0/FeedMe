package com.example.feedme.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipes_favorite")
class RecipeFavorite (

    @PrimaryKey(autoGenerate = false)
    var recipe_id: Int,

    var dateAdded: Date = Date(System.currentTimeMillis().times(1000)),

    )