package com.example.justshop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = md_theme_dark_primary,
    background = md_theme_dark_background,
    secondary = md_theme_dark_secondary,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    onPrimary =md_theme_dark_onPrimary
)

private val LightColorPalette = lightColors(
    primary = md_theme_light_primary,
    background = md_theme_light_background,
    secondary = md_theme_light_secondary,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    onPrimary =md_theme_light_onPrimary
)


@Composable
fun JustShopTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}