package com.example.feedme.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.R
import kotlin.math.roundToInt

@Preview
@Composable
fun RecipeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp)
            .clickable(onClick = {

                // Onclick => Page Perso de la recette

//                    val intent = Intent(context, DetailActivity::class.java)
//                    intent.putExtra("itemId", itemId)
//                    context.startActivity(intent)

            }),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.recipe_result),
                contentDescription = "Image du restaurant",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Pizza Potato Skins",
                modifier = Modifier.padding(top = 5.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type de cuisine",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    // isFavorite.value => Favoris value true/false Icon
                    imageVector = if (true) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Ajouter/Retirer des favoris",
                    // isFavorite.value => Favoris value true/false Couleur
                    tint = if (true) Color.Yellow else Color.Transparent,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {

                            // Onclick change Favorite value

//                        isFavorite.value = !isFavorite.value
//                        item.isFavorite = isFavorite.value
//                        viewModel.toggleFavorite(item)
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Surface(modifier = Modifier.width(40.dp), shape = RoundedCornerShape(100), color = Color(0xFFEEEEEE)) {
                    Text(
                        color = Color.Black,
                        text = ConvertRating(16f).toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "TEST",
                    fontSize = 14.sp,
                    color = Color.Green
                )
            }
        }
    }
}


fun ConvertRating(rating: Float): Float {
    val rating5 = rating / 4.0f
    return (rating5 * 10.0f).roundToInt() / 10.0f
}