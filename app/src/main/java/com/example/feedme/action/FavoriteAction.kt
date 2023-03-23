package com.example.feedme.action

import com.example.feedme.database.FavoriteDao
import com.example.feedme.domain.RecipeFavorite
import com.example.feedme.domain.RecipeWithFavorite

class FavoriteAction(private val favoriteDao: FavoriteDao) {

    suspend fun searchFavorites(query: String?, page: Int): List<RecipeWithFavorite> {

        return if (!query.isNullOrBlank())
            favoriteDao.searchFavoritesWithQuery(query, page)
        else
            favoriteDao.searchFavorites(page)
    }

    suspend fun deleteFavorite(recipeFavorite : RecipeFavorite)
    {
        favoriteDao.delete(recipeFavorite)
    }
    suspend fun  Count() : Int {
        return favoriteDao.countFavorite();
    }


}