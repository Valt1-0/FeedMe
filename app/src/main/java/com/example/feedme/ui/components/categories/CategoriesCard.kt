package com.example.feedme.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ui.components.categories.CategoriesItem
import com.example.feedme.ui.components.viewModel.HomeViewModel
import com.example.feedme.ui.theme.CategoriesShape

@Composable
fun CategoriesCard(
    onClick: () -> Unit,
    navigateToFavoriteList: (String) -> Unit,
    viewModel: HomeViewModel,
    categorie: CategoriesItem,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .padding()
            .clickable(onClick = {
                viewModel.onQueryChange(categorie.query)

                onClick()
                navigateToFavoriteList("accueil")
            }), elevation = 3.dp, shape = CategoriesShape.medium
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = categorie.image),
                contentDescription = categorie.name,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categorie.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6,
                    fontSize = 15.sp,
                )
            }
        }
    }
}