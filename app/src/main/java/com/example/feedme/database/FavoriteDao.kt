package com.example.feedme.database

import androidx.room.*
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite

@Dao
interface FavoriteDao {
    @Query(
        """SELECT recipes.*, CASE WHEN recipes_favorite.recipe_id IS NOT NULL THEN 1 ELSE 0 END AS favorite  FROM recipes_favorite 
                INNER JOIN recipes ON recipes_favorite.recipe_id = recipes.id
             ORDER BY recipes.id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)"""
    )
    suspend fun searchFavorites(page: Int, pageSize: Int): List<RecipeWithFavorite>


    @Query(
        """SELECT recipes.*, CASE WHEN recipes_favorite.recipe_id IS NOT NULL THEN 1 ELSE 0 END AS favorite  FROM recipes_favorite 
                INNER JOIN recipes ON recipes_favorite.recipe_id = recipes.id
                 WHERE recipes.title LIKE '%' || :query || '%'
                    OR recipes.ingredients LIKE '%' || :query || '%'
             ORDER BY recipes.id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)"""
    )
    suspend fun searchFavoritesWithQuery(
        query: String,
        page: Int,
        pageSize: Int,
    ): List<RecipeWithFavorite>

    @Query("SELECT COUNT(*) FROM recipes_favorite")
    suspend fun countFavorite(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeFavorite: RecipeFavorite)

    @Delete
    suspend fun delete(recipeFavorite: RecipeFavorite)

    @Query("SELECT EXISTS(SELECT 1 FROM recipes_favorite WHERE recipe_id = :recipeId LIMIT 1)")
    suspend fun isFavorite(recipeId: Int): Boolean


}