package com.example.feedme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.RecipeCard


@Composable
fun FavoritesList(
    recipes: List<RecipeWithFavorite>,
    OnFavoriteClick: (Int, Boolean) -> Unit,
    navigateToFavoriteList: (String) -> Unit
) {
    val favorites = listOf("recette1", "recette2", "recette3", "recette4", "recette5")



    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Vos Favoris",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )

        }
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 380.dp, height = 230.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemsIndexed(recipes) { index, recipe ->
                    RecipeCard(recipe,OnFavoriteClick)
                }
            }
            if (recipes.size > 9) {
                Button(
                    onClick = { navigateToFavoriteList("favoris")},
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text("Voir plus")
                }
            }
        }
    }
}
