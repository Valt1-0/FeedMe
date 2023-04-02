package com.example.feedme.util

import com.example.feedme.database.model.Recipe
import com.example.feedme.network.model.RecipeDto
import java.util.*


class RecipeDtoMapper : EntityMapper<RecipeDto, Recipe> {
    override fun mapFromEntity(entity: RecipeDto): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl,
            ingredients = convertIngredientListToString(entity.ingredients),
            dateAdded = convertLongToDate(entity.longDateAdded),
            dateUpdated = convertLongToDate(entity.longDateUpdated)
        )
    }

    private fun convertIngredientListToString(ingredients: List<String>): String {
        val ingredientsString = StringBuilder()
        for (ingredient in ingredients) {
            ingredientsString.append("$ingredient,")
        }
        return ingredientsString.toString()
    }

    private fun convertLongToDate(longDate: Long?): Date? {
        return longDate?.let { Date(it) }
    }

    fun toRecipeList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapFromEntity(it) }
    }
}
