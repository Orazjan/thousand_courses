package com.atnzvdev.thousand.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppDarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,

    background = DarkBackground,
    onBackground = Color.White,

    surface = DarkSurface,
    onSurface = Color.White,

    surfaceVariant = DarkSurface,
    onSurfaceVariant = GrayText,

    outlineVariant = DividerGray
)

@Composable
fun ThousandTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography = Typography,
        content = content
    )
}