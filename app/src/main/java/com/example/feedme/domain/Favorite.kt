package com.example.feedme.domain

import com.example.feedme.database.FavoriteDao
import javax.inject.Inject

class Favorite @Inject constructor(
    private val favoriteDao: FavoriteDao,
) {

    suspend fun Insert(
        id: Int,
    ) {
        try {
            val recipeFavorite = RecipeFavorite(id)
            favoriteDao.insert(recipeFavorite)
        } catch (e: Exception) {

        }
    }

}