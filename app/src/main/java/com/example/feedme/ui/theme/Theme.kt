package com.example.feedme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Black,
    secondary = Black,
    secondaryVariant = Trans

)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = White,
    secondary = White,
    secondaryVariant = Trans
)

@Composable
fun FeedMeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    systemUiController.setNavigationBarColor(
        color = colors.primary,
    )
    systemUiController.setStatusBarColor(
        color = colors.secondaryVariant,
        darkIcons = false
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes

    )
    {
        // Définir la couleur de texte en fonction du thème actuel
        val textColor = if (darkTheme) colors.primary else colors.primary

        // Utiliser la couleur de texte pour tous les Text()
        CompositionLocalProvider(LocalContentColor provides textColor) {
            content()
        }

    }
}