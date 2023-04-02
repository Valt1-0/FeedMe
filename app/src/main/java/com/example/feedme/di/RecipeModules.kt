package com.example.feedme.di

import com.example.feedme.network.RecipeService
import com.example.feedme.ui.components.MainRepository
import com.example.feedme.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object
RecipeModules {
    @Singleton
    @Provides
    fun provideApiService(): RecipeService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RecipeService::class.java)
    }

    @Provides
    fun provideMainRepository(recipeService: RecipeService): MainRepository {
        return MainRepository(recipeService = recipeService)
    }
}
