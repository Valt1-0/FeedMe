package com.example.feedme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedme.domain.Recipe
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
        SELECT * FROM recipes  
        WHERE title LIKE '%' || :query || '%'
        OR ingredients LIKE '%' || :query || '%' 
        ORDER BY id DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
      fun searchRecipes(
        query: String,
        page: Int,
        pageSize: Int = RECIPE_PER_PAGE
    ): List<Recipe>

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