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

//    @Insert
//    suspend fun insertRecipe(recipe: Recipe): Long
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

////    @Query("SELECT * FROM recipes WHERE id = :id")
////    suspend fun getRecipeById(id: Int): Recipe?
//
//    @Query("DELETE FROM recipes WHERE id IN (:ids)")
//    suspend fun deleteRecipes(ids: List<Int>): Int
//
    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()
//
//    @Query("DELETE FROM recipes WHERE id = :primaryKey")
//    suspend fun deleteRecipe(primaryKey: Int): Int

    /**
     * Retrieve recipes for a particular page.
     * Ex: page = 2 retrieves recipes from 30-60.
     * Ex: page = 3 retrieves recipes from 60-90
     */
    @Query("""
        SELECT recipes.*, CASE WHEN recipes_favorite.recipe_id IS NOT NULL THEN 1 ELSE 0 END AS favorite FROM recipes 
         LEFT JOIN recipes_favorite ON recipes.id = recipes_favorite.recipe_id
        WHERE recipes.title LIKE '%' || :query || '%'
        OR recipes.ingredients LIKE '%' || :query || '%' 
        ORDER BY recipes.id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
      fun searchRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PER_PAGE
    ): List<RecipeWithFavorite>

    /**
     * Same as 'searchRecipes' function, but no query.
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