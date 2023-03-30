package com.example.feedme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.RecipeCard


@Composable
fun FavoritesList(
    recipes: List<RecipeWithFavorite>,
    OnFavoriteClick: (Int, Boolean) -> Unit,
    navigateToFavoriteList: (String) -> Unit,
) {

    if (recipes.size > 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Vos Favoris",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                if (recipes.size > 9) {
                    ClickableText(
                        modifier = Modifier.padding(top = 20.dp),
                        text = AnnotatedString("Voir plus"),
                        onClick = {
                            navigateToFavoriteList("favoris")
                        }
                    )
                }

            }
            Box(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(width = 380.dp, height = 230.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(recipes) { index, recipe ->
                        RecipeCard(recipe, OnFavoriteClick, navigateToFavoriteList)
                    }
                }
            }
        }
        Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
    }
}
