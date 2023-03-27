package com.example.feedme.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import com.example.feedme.Categories.CategoriesItem

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val BottomCardShape = Shapes(
    large = RoundedCornerShape(topStart = 80.dp)
)

val InputShape = Shapes(
    large = RoundedCornerShape(80)
)

val CategoriesShape  = Shapes(
    medium = RoundedCornerShape(10)
)