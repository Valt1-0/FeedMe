package com.example.feedme.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_favorite")
class RecipeFavorite (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var recipe_id: Int,

)