package com.example.feedme

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.LoadImageFromUrl
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FavoriteCard(
    recipe: RecipeWithFavorite,
    OnFavoriteClick: (Int, Boolean) -> Unit,
    NavigateToRecipeDetails: (String) -> Unit,
) {

    var favorite = recipe.favorite
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    println("DateRecipe : " + recipe.dateUpdated.toString())
    println("recipe.dateUpdated : " + sdf.format(recipe.dateUpdated))
    println("recipe.favorite" + recipe.favorite.toString())
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = false // start the animation immediately
        }
    }

    val longDateAdded = recipe.dateAdded
    println("longDateAdded: $longDateAdded")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .clickable(onClick = {
                visibleState.targetState = !visibleState.targetState
                NavigateToRecipeDetails("recipeDetails/${recipe.id}")
            }),
        elevation = 0.dp,
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            LoadImageFromUrl(
                LocalContext.current,
                recipe.featuredImage,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 5.dp).fillMaxHeight()
            ) {
                Text(text = recipe.title + " - " + recipe.publisher)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis à jour : " + sdf.format(recipe.dateUpdated),
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        modifier = Modifier.size(26.dp, 23.dp),
                        shape = CircleShape,
                        color = Color(0xFFEEEEEE)
                    ) {
                        Text(
                            color = Color.Black,
                            text = ConvertRating(recipe.rating).toString(),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        )
                    }
                }
            }
        }


    }

}

fun ConvertRating(rate: Int): Float {
    return (rate.toFloat() / 20.0f)
}