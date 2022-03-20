package com.javalon.xpensewhiz.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.javalon.xpensewhiz.util.CompactSpacing
import com.javalon.xpensewhiz.util.ExpandedSpacing
import com.javalon.xpensewhiz.util.LocalSpacing
import com.javalon.xpensewhiz.util.MediumSpacing
import com.javalon.xpensewhiz.util.WindowInfo
import com.javalon.xpensewhiz.util.rememberWindowInfo

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,

    background = blueBGDay,
    surface = cardBGDay,
    onSurface = blueText,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black
)

@Composable
fun XpenseWhizTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val windowInfo = rememberWindowInfo()

    CompositionLocalProvider(
        when (windowInfo.screenHeightInfo) {
            is WindowInfo.WindowType.Compact -> {
                LocalSpacing provides CompactSpacing()
            }
            is WindowInfo.WindowType.Medium -> {
                LocalSpacing provides MediumSpacing()
            }
            else -> LocalSpacing provides ExpandedSpacing()
        }
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}