package com.example.feedme.action

import com.example.feedme.database.FavoriteDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite

class FavoriteAction(private val favoriteDao: FavoriteDao,private val query: String,private val page: Int,private val pageSize : Int) {

    suspend fun searchFavorites(): List<RecipeWithFavorite> {

        return if (query.isNotBlank())
            favoriteDao.searchFavoritesWithQuery(query, page,pageSize)
        else
            favoriteDao.searchFavorites(page,pageSize)
    }

    suspend fun deleteFavorite(recipeFavorite : RecipeFavorite)
    {
        favoriteDao.delete(recipeFavorite)
    }
    suspend fun  Count() : Int {
        return favoriteDao.countFavorite();
    }

    suspend fun getMaxFavorite(MaxFavorite: Int)
    {

    }


}