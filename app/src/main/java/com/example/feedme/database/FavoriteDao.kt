package com.example.feedme.database

import androidx.room.*
import com.example.feedme.domain.RecipeFavorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM recipes_favorite")
    suspend fun getAll(): List<RecipeFavorite>

    @Query("SELECT COUNT(*) FROM recipes_favorite WHERE recipe_id = :recipeId")
    suspend fun countFavorite(recipeId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeFavorite: RecipeFavorite)

    @Delete
    suspend fun delete(recipeFavorite: RecipeFavorite)

    @Query("SELECT EXISTS(SELECT 1 FROM recipes_favorite WHERE recipe_id = :recipeId LIMIT 1)")
    suspend fun isFavorite(recipeId: Int): Boolean
}