package com.example.feedme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.feedme.database.model.Recipe
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.util.Constants
import com.example.feedme.util.Converters


@Database(entities = [Recipe::class, RecipeFavorite::class], version = 16)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        val DATABASE_NAME: String = Constants.DATABASE_NAME
    }
}
