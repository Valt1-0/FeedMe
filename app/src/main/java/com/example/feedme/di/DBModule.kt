package com.example.feedme.di

import androidx.room.Room
import com.example.feedme.BaseApplication
import com.example.feedme.database.AppDatabase
import com.example.feedme.database.FavoriteDao
import com.example.feedme.database.Migration1To2
import com.example.feedme.database.RecipeDao
import com.example.feedme.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addMigrations(Migration1To2())
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao {
        return db.recipeDao()
    }

    @Singleton
    @Provides
    fun provideRecipeFavoriteDao(db: AppDatabase): FavoriteDao {
        return db.favoriteDao()
    }
}