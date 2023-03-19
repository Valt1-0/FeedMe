package com.example.feedme.SplashScreen


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Snackbar() {
    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Snackbar(
            action = {
                Button(onClick = { }) {
                    Text(text = "Réessayer")
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Icon(
                    modifier = Modifier.size(17.dp),
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pas de connexion Internet",
                    color = Color.White
                )
            }
        }
    }
}
