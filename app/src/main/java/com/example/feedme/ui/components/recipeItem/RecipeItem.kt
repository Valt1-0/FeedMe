package com.example.feedme.ui.components.recipeItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

fun convertIngredientStringToList(ingredientString: String):List<String>{
    return ingredientString.split(",")
}

@Composable
fun RecipeDetails(
    recipeId: Int?
){
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val ingredientString = "pomme,myonnaise,boeuf,poisson"
    val itemsListString = convertIngredientStringToList(ingredientString)
    val recipe = RecipeWithFavorite(id = 0,title = "recette test", publisher = "anonyme", featuredImage = "https://nyc3.digitaloceanspaces.com/food2fork/food2fork-static/featured_images/573/featured_image.png", rating = 50, sourceUrl = "", ingredients = "pomme, myonnaise, boeuf, poisson", dateAdded = Date (1606348867), dateUpdated = Date(1606348867),favorite = false )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    LoadImageFromUrl(LocalContext.current, recipe.featuredImage)
                }
                Text(
                    text = recipe.title + "-" + recipe.publisher,
                    modifier = Modifier.padding(top = 5.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = sdf.format(recipe.dateUpdated),
                    color = Color(0xFFAAAAAA),
                    modifier = Modifier.padding(top = 5.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            itemsIndexed(items = itemsListString){
                index, recipe-> Row() {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = recipe,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }


           /* Surface(
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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row() {
                Text(
                    text = ingredientString,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }*/
        }
    }
}
