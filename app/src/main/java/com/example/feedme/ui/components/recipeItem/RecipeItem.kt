package com.example.feedme.ui.components.recipeItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import com.example.feedme.database.model.Recipe
import com.example.feedme.navigation.Screen
import com.example.feedme.ui.components.RecipeCard

/*fun listItem() {
    val recipeItem = listOf("pomme", "banane", "boeuf")
}*/
/*
import android.content.ClipData.Item
import androidx.compose.runtime.Composable

fun listItem(): List<Item> {
    val client = HttpClient(Android) {

    }
    return client.get("https://example.com/api/ingredients")
}
@Composable

val ingredients by remember { mutableStateOf(emptyList<Ingredient>()) }
LaunchedEffect(Unit) {
    ingredients = getIngredients()
}
LazyColumn {
    items(ingredients) { ingredient ->
        Text(ingredient.name)
    }
}*/


@Composable
fun recipeItemsList(recipes: List<Screen.RecipeDetails>, OnClick : (Int, Boolean) -> Unit){
    val itemsList = listOf("ingrédient1", "ingédient2", "ingédient3", "ingédient4", "ingédient5")

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Liste d'ingrédient",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .size(width = 380.dp, height = 230.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            itemsListIndexed(recipes) {
                index, recipe -> RecipeCard(recipe, OnClick)
            }
        }
    }
}

