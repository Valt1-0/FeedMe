package com.example.feedme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feedme.ui.theme.MainTheme
import androidx.navigation.compose.rememberNavController
import com.example.feedme.Components.RecipeCard
import com.example.feedme.Components.SearchBar
import java.lang.Math.round


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
fun AccueilScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchBar()

        Divider(modifier = Modifier.height(7.dp), color = Color(0xFFEEEEEE))
        RecipeCard()
    }
}

@Composable
fun ParcourirScreen() {
    Text(text = "Parcourir")
}
