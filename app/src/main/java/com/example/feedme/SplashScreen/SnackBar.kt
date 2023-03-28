package com.example.feedme.SplashScreen


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ui.components.viewModel.HomeViewModel

@Composable
fun Snackbar(
    reloadActivity: () -> Unit,
    continuWithoutConnexion: () -> Unit?,
    viewModel: HomeViewModel,
) {
    var recipeInDB = viewModel.recipeInDB.value

    LaunchedEffect(Unit) {
        viewModel.recipeInDB()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Snackbar(
            modifier = Modifier.padding(10.dp),
            contentColor = Color(0xff333333),
            backgroundColor = Color(0xff333333),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                NoConnectionText()

                Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    if (recipeInDB) {
                        Button(onClick = {
                            continuWithoutConnexion.invoke()
                        }) {
                            Text(fontSize = 12.sp, text = "Continuer sans connexion")
                        }
                    }
                    Button(modifier = Modifier, onClick = {
                        reloadActivity.invoke()
                    }) {
                        Text(fontSize = 12.sp, text = "Réessayer")
                    }
                }
            }
        }
    }
}
//

@Composable
fun NoConnectionText() {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Pas de connexion Internet",
            color = Color.White
        )
    }
}