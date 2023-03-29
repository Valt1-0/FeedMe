package com.example.feedme.ui.components


import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
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
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun RecipeCard(
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

   // RecipeDetails(visibleState,recipe)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 380.dp, height = 240.dp)
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .clickable(onClick = {
                visibleState.targetState = !visibleState.targetState
                NavigateToRecipeDetails("recipeDetails/${recipe.id}")
                // Onclick => Page Perso de la recette

//                    val intent = Intent(context, DetailActivity::class.java)
//                    intent.putExtra("itemId", itemId)
//                    context.startActivity(intent)

            }),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadImageFromUrl(LocalContext.current, recipe.featuredImage)

                Icon(
                    // isFavorite.value => Favoris value true/false Icon
                    imageVector = if (favorite) Icons.Filled.Star else Icons.TwoTone.Star,
                    contentDescription = "Ajouter/Retirer des favoris",
                    // favorite.value => Favoris value true/false Couleur
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp)
                        .clickable(onClick = {

                            favorite = !favorite

                            OnFavoriteClick(recipe.id, favorite)
                            // OnFavoriteClick(recipe.id, viewModel)

                        })


                )
            }
            Text(
                // Nom recette plus publisher
                text = recipe.title + " - " + recipe.publisher,
                modifier = Modifier.padding(top = 5.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    // Date de mise à jour

                    text = "Mis à jour : " + sdf.format(recipe.dateUpdated),
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
fun ConvertRating(rate: Int): Float {
    return (rate.toFloat() / 20.0f)
}