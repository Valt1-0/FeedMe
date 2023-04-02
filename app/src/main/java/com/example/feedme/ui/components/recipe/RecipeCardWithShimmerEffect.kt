package com.example.feedme.ui.components.recipe

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardWithShimmerEffect() {

    // create a gradient brush for the shimmer effect
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
        Color.LightGray.copy(alpha = 0.9f)
    )

    // use an infinite transition to animate the shimmer effect
    val transition = rememberInfiniteTransition()

    // animate the gradient from left to right
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )

    // create the brush with the gradient and the animated offset
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 380.dp, height = 230.dp)
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 10.dp),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush)

                        .height(150.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(90.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Surface(
                    modifier = Modifier.size(26.dp, 23.dp),
                    shape = CircleShape,
                    color = Color(0xFFEEEEEE)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(4.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .size(50.dp)
                            .background(brush)
                    )
                }
            }
        }
    }
}