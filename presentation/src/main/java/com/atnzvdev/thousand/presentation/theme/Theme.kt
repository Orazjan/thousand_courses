package com.atnzvdev.thousand.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppDarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = White,

    secondary = GreenAccent,
    onSecondary = White,

    background = DarkBackground,
    onBackground = White,

    surface = DarkSurface,
    onSurface = White,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = GrayText,

    error = ErrorRed,
    onError = White,

    outlineVariant = DividerColor,
    outline = Transparent,
    scrim = ScrimBlack
)

@Composable
fun ThousandTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppDarkColorScheme,
        typography = Typography,
        content = content
    )
}