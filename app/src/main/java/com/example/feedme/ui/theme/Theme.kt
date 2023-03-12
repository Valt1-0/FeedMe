package com.example.feedme.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Black,
    secondary = Black,
    secondaryVariant = Trans

)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = WhiteGrey,
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
//    val useDarkIcons = !isSystemInDarkTheme()

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


@SuppressLint("ConflictingOnColor")
private val DarkOnboardColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Black,
    secondary = LightBlack,
    onPrimary = White,
)

@SuppressLint("ConflictingOnColor")
private val LightOnboardColorPalette = lightColors(
    primary = Primary,
    primaryVariant = White,
    secondary = White,
    onPrimary = Black,
)

@Composable
fun OnBoardingTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkOnboardColorPalette
    } else {
        LightOnboardColorPalette
    }

    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = !isSystemInDarkTheme()

    systemUiController.setNavigationBarColor(
        color = colors.secondary,
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