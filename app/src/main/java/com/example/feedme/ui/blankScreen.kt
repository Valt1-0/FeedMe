package com.example.feedme.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun blankScreen(image : Int,text : String)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pulseMagnitude = 1.5f
            val pulseAnim = rememberInfiniteTransition()
                .animateFloat(
                    initialValue = 1f,
                    targetValue = pulseMagnitude,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 3000,
                            easing = LinearEasing
                        )
                    )
                )

            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .graphicsLayer {
                        scaleX = pulseAnim.value
                        scaleY = pulseAnim.value
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}