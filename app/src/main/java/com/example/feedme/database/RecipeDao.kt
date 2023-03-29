package com.example.feedme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedme.database.model.Recipe
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.util.Constants.RECIPE_PER_PAGE


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    @Query(
        """
        SELECT recipes.*, CASE WHEN recipes_favorite.recipe_id IS NOT NULL THEN 1 ELSE 0 END AS favorite FROM recipes 
         LEFT JOIN recipes_favorite ON recipes.id = recipes_favorite.recipe_id
        WHERE recipes.title LIKE '%' || :query || '%'
        OR recipes.ingredients LIKE '%' || :query || '%' 
        ORDER BY recipes.id ASC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """
    )
    fun searchRecipesWithQuery(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PER_PAGE,
    ): List<RecipeWithFavorite>

    @Query(
        """
        SELECT recipes.*, CASE WHEN recipes_favorite.recipe_id IS NOT NULL THEN 1 ELSE 0 END AS favorite FROM recipes 
         LEFT JOIN recipes_favorite ON recipes.id = recipes_favorite.recipe_id
        ORDER BY recipes.id ASC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """
    )
    fun searchRecipes(
        page: Int,
        pageSize: Int = RECIPE_PER_PAGE,
    ): List<RecipeWithFavorite>

    @Query("SELECT CASE WHEN EXISTS(SELECT * FROM recipes LIMIT 1) THEN 1 ELSE 0 END")
    suspend fun recipeInDB(): Boolean

    /**
     * Same as 'searchRecipesWithQuery' function, but no query.
     */
//    @Query("""
//        SELECT * FROM recipes
//        ORDER BY date_updated DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
//    """)
//    suspend fun getAllRecipes(
//        page: Int,
//        pageSize: Int = RECIPE_PAGINATION_PAGE_SIZE
//    ): LiveData<List<Recipe>>

}