package com.example.feedme.ui.components.recipeItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.feedme.database.model.Recipe
import com.example.feedme.navigation.Screen
import com.example.feedme.ui.components.ConvertRating
import com.example.feedme.ui.components.LoadImageFromUrl
import com.example.feedme.ui.components.RecipeCard
import java.text.SimpleDateFormat
import java.util.*
import com.example.feedme.domain.RecipeWithFavorite as RecipeWithFavorite

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

fun convertIngredientStringToList(ingredientString: String):List<String>{
    return ingredientString.split(",")
}

@Composable
fun recipeItemsList(
    recipe: RecipeWithFavorite
){
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val ingredientString = "pomme, myonnaise, boeuf, poisson"
    val itemsListString = convertIngredientStringToList(ingredientString)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            LoadImageFromUrl(LocalContext.current,recipe.featuredImage)

        Text(
            text = recipe.title + "-" + recipe.publisher,
            modifier = Modifier.padding(top = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Row (
            modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = sdf.format(recipe.dateUpdated),
                color = Color(0xFFAAAAAA),
                modifier = Modifier.padding(top = 5.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Surface(
                modifier = Modifier.size(26.dp, 23.dp),
                shape = CircleShape,
                color = Color(0xFFEEEEEE)
            ){
                Text(
                    color = Color.Black,
                    text = ConvertRating(recipe.rating).toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                )
            }
            Row() {
                Text(
                    text = "Liste d'ingrédient :",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        }
    }
}
