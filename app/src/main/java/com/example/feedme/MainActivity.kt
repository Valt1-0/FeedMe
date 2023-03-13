package com.example.feedme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
fun TextSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChanged(it) },
        value = value,
        onValueChange = { query ->
            onValueChanged(query)
        },
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
            )
        },
        trailingIcon = {
            IconButton(onClick = { onClearClick() }) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
            }
        },
        keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFAA0000), // Utilisez la couleur primaire de Material Theme ici
            unfocusedBorderColor = colors.primary.copy(alpha = 0.5f) // Vous pouvez également modifier l'opacité de la couleur primaire ici
        )
    )
}

@Preview
@Composable
fun Search() {
    TextSearchBar(value = "...", label = "Recherche", onValueChanged = {  })
}

@Composable
fun AccueilScreen() {
    Text(text = "Accueil")
}

@Composable
fun ParcourirScreen() {
    Text(text = "Parcourir")
}