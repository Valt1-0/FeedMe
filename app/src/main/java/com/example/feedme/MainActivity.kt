package com.example.feedme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feedme.ui.theme.FeedMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedMeTheme {
          
                // A surface container using the 'background' color from the theme
               Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                  Text(text = "Hello word ! ")
               }
            }
        }
       
    }
}

