package com.stayspotter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.stayspotter.Constant

private val MainColorTheme = darkColorScheme(
    primary = Constant.EDGE_BLUE,
    secondary = Constant.LIGHT_EDGE_BLUE,
    tertiary = Constant.PETRIFIED_BLUE,
    onPrimary = Constant.TEXT_GRAY,
    onSecondary = Constant.TEXT_GRAY,
    onTertiary = Constant.TEXT_GRAY,
    background = Constant.BACKGROUND_COLOR,
    surfaceTint = Constant.EDGE_BLUE,
    surface = Constant.EDGE_BLUE,
)

private val NavigationTheme = darkColorScheme(
    primary = Constant.LIGHT_EDGE_BLUE,
    surfaceTint = Constant.DARK_GRAY,
    surface = Constant.DARK_GRAY,
    surfaceVariant = Constant.PETRIFIED_BLUE,
    primaryContainer = Constant.PETRIFIED_BLUE,
)

@Composable
fun StaySpotterTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MainColorTheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun NavBarTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NavigationTheme,
        typography = Typography,
        content = content
    )
}