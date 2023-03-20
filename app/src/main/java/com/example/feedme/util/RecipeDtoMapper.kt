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
           // description = entity.description,
          //  cookingInstructions = entity.cookingInstructions,
            ingredients = convertIngredientListToString(entity.ingredients),
            dateAdded = convertLongToDate(entity.longDateAdded),
            dateUpdated = convertLongToDate(entity.longDateUpdated)
        )
    }

//    override fun mapToEntity(domainModel: Recipe): RecipeDto {
//        return RecipeDto(
//            pk = domainModel.id,
//            title = domainModel.title,
//            publisher = domainModel.publisher,
//            featuredImage = domainModel.featuredImage,
//            rating = domainModel.rating,
//            sourceUrl = domainModel.sourceUrl
//         //   description = domainModel.description,
//          //  cookingInstructions = domainModel.cookingInstructions,
//           // ingredients = domainModel.ingredients
//        )
//    }

    private fun convertIngredientListToString(ingredients: List<String>): String {
        val ingredientsString = StringBuilder()
        for(ingredient in ingredients){
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

//    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
//        return initial.map { mapToEntity(it) }
//    }
}
