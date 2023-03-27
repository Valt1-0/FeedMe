package com.example.feedme.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.Onboarding.OnboardingItem
import com.example.feedme.ui.components.SearchBar
import com.example.feedme.ui.components.favorite.EventTrigger
import com.example.feedme.ui.theme.BottomCardShape
import com.example.feedme.ui.theme.CategoriesShape

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoriesScreen(onClick: () -> Unit) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Catégories") },
//                navigationIcon = {
//                    IconButton(onClick = onClick) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
//                    }
//                }
//            )
//        })
//    { contentPadding ->
//          modifier = Modifier.padding(contentPadding)
//    }

//    SearchBar(
//        query = query, onSearch = { favoriteViewModel.onEventTrigger(EventTrigger.SearchEvent) },
//        onQueryChange = favoriteViewModel::onQueryChange
//    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categoriesItem) { categorie ->
            Card(
                modifier = Modifier
                    .padding()
                    .clickable(onClick = {
                        onClick
                    }),
                elevation = 3.dp,
                shape = CategoriesShape.medium
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
    }
}
