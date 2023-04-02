package com.example.feedme.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ui.components.viewModel.HomeViewModel
import com.example.feedme.ui.theme.CategoriesShape

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesScreen(
    onClick: () -> Unit,
    navigateToFavoriteList: (String) -> Unit,
    viewModel: HomeViewModel,
) {

    LazyVerticalGrid(
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 7.dp),
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categoriesItem) { categorie ->
            CategoriesCard(categorie = categorie, onClick = onClick, viewModel = viewModel, navigateToFavoriteList = navigateToFavoriteList, modifier = Modifier )
        }
    }
}

