package com.example.feedme.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipes")
class Recipe (
    @PrimaryKey(autoGenerate = false)
    var id: Int,

    var title: String,

    var publisher: String,

    var featuredImage: String,

    var rating: Int,

    var sourceUrl: String,

    var ingredients: String = "",

    var dateAdded: Date?,

    var dateUpdated: Date?

)