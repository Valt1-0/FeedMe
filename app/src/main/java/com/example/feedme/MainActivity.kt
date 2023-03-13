package com.example.feedme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feedme.ui.theme.MainTheme
import androidx.navigation.compose.rememberNavController
import com.example.feedme.ui.theme.BottomCardShape
import com.example.feedme.ui.theme.InputShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = navController.currentDestination?.route == "accueil",
                                onClick = { navController.navigate("accueil") },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                                label = { Text("Accueil") },
                                selectedContentColor = Color.Gray,
                                unselectedContentColor = Color.White,

                                )
                            BottomNavigationItem(
                                selected = navController.currentDestination?.route == "parcourir",
                                onClick = { navController.navigate("parcourir") },
                                icon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Parcourir"
                                    )
                                },
                                label = { Text("Parcourir") },
                                selectedContentColor = Color.Gray,
                                unselectedContentColor = Color.White,
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController, startDestination = "accueil") {
                            composable("accueil") { AccueilScreen() }
                            composable("parcourir") { ParcourirScreen() }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SearchBar() {
    val query = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier.background(color = colors.primaryVariant),
                value = query.value,
                onValueChange = { query.value = it },
                placeholder = { Text(text = "Recettes ...") },
                maxLines = 1,
                leadingIcon = {Icon(Icons.Default.Search,contentDescription = "Recherche")},
                trailingIcon =  {
                    IconButton(onClick = { query.value = "" }) {Icon(Icons.Default.Clear, contentDescription = "Clear")}
                    },
                shape = InputShape.large,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),

            )
        }
    }

//        LazyColumn {
//            itemsIndexed(
//                items = recipes
//            ) { index, recipe ->
//                RecipeCard(recipe = recipe, onClick = {})
//            }
//        }

}

@Composable
fun AccueilScreen() {
    SearchBar()
}

@Composable
fun ParcourirScreen() {
    Text(text = "Parcourir")
}