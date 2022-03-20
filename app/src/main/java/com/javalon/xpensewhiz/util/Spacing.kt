package com.javalon.xpensewhiz.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
open class Spacing(
    open val default: Dp = 0.dp,
    open val extraSmall: Dp = 0.dp,
    open val small: Dp = 0.dp,
    open val medium: Dp = 0.dp,
    open val large: Dp = 0.dp,
    open val extraLarge: Dp = 0.dp,
    open val sheetHeight: Float = 0f
)

@Immutable
data class CompactSpacing(
    override val default: Dp = 0.dp,
    override val extraSmall: Dp = 2.dp,
    override val small: Dp = 4.dp,
    override val medium: Dp = 8.dp,
    override val large: Dp = 16.dp,
    override val extraLarge: Dp = 32.dp,
    override val sheetHeight: Float = 1f
) : Spacing(
    default, extraSmall, small, medium, large, extraLarge, sheetHeight
)

@Immutable
data class MediumSpacing(
    override val default: Dp = 0.dp,
    override val extraSmall: Dp = 4.dp,
    override val small: Dp = 8.dp,
    override val medium: Dp = 16.dp,
    override val large: Dp = 32.dp,
    override val extraLarge: Dp = 64.dp,
    override val sheetHeight: Float = 0.8f
) : Spacing(default, extraSmall, small, medium, large, extraLarge, sheetHeight)

@Immutable
data class ExpandedSpacing(
    override val default: Dp = 0.dp,
    override val extraSmall: Dp = 8.dp,
    override val small: Dp = 16.dp,
    override val medium: Dp = 32.dp,
    override val large: Dp = 64.dp,
    override val extraLarge: Dp = 128.dp,
    override val sheetHeight: Float = 0.7f
) : Spacing(default, extraSmall, small, medium, large, extraLarge, sheetHeight)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current