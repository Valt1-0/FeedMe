package com.example.feedme.ui.components


import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.feedme.R
import com.example.feedme.domain.Recipe
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun RecipeCard(recipe:Recipe) {

    val favorite = remember { mutableStateOf(false) }
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    println("DateRecipe : " + recipe.dateUpdated.toString())
println("recipe.dateUpdated : " + sdf.format(recipe.dateUpdated))



    val longDateAdded = recipe.dateAdded
    println("longDateAdded: $longDateAdded")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .clickable(onClick = {

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
                Image(
                    painter = // Set options for the image request, such as resizing or caching.
                    // For example:
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = recipe.featuredImage)
                            .apply(block = fun ImageRequest.Builder.() {
                                // Set options for the image request, such as resizing or caching.
                                // For example:
                                size(300, 300)
                                placeholder(R.drawable.jonathan)
                            }).build()
                    ),
                    contentDescription = "Image du restaurant",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    // isFavorite.value => Favoris value true/false Icon
                    imageVector = if (favorite.value) Icons.Filled.Star else Icons.TwoTone.Star,
                    contentDescription = "Ajouter/Retirer des favoris",
                    // isFavorite.value => Favoris value true/false Couleur
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp, end = 8.dp)
                        .clickable(onClick = {
                            favorite.value = !favorite.value

                            // ADD BDD
                        })


                )
            }
            Text(
                // Nom recette plus publisher
                text = recipe.title + " - " + recipe.publisher ,
                modifier = Modifier.padding(top = 5.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    // Date de mise à jour

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
                ) {
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
            }
        }
    }
}


fun ConvertRating(rate: Int): Float {
    return (rate.toFloat() / 20.0f)
}