package com.example.feedme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.feedme.domain.Recipe
import com.example.feedme.util.Constants
import com.example.feedme.util.Converters


@Database(entities = [Recipe::class ], version = 7)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object{
        val DATABASE_NAME: String = Constants.DATABASE_NAME
    }
}