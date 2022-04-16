package com.javalon.xpensewhiz.presentation.setting_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Switch(
    modifier: Modifier = Modifier,
    switch: Boolean = false,
    scale: Float = 2f,
    width: Dp = 28.dp,
    height: Dp = 16.dp,
    checkedTrackColor: Color = MaterialTheme.colors.primary,
    uncheckedTrackColor: Color = Color.DarkGray.copy(alpha = 0.5f),
    thumbColor: Color = Color.White,
    gapBetweenThumbAndTrackEdge: Dp = 1.5.dp,
    onSwitched: (Boolean) -> Unit
) {
    val switchOn = remember {
        mutableStateOf(switch)
    }

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge
    val animatePosition = animateFloatAsState(
        targetValue = if (switchOn.value) {
            onSwitched(switchOn.value)
            with(LocalDensity.current) {
                width.toPx() - thumbRadius.toPx() - gapBetweenThumbAndTrackEdge.toPx()
            }
        } else {
            onSwitched(switchOn.value)
            with(LocalDensity.current) {
                gapBetweenThumbAndTrackEdge.toPx() + thumbRadius.toPx()
            }
        }
    )

    Canvas(modifier = modifier
        .size(width = width, height = height)
        .scale(scale)
        .pointerInput(true) {
            detectTapGestures (
                onTap = { switchOn.value = !switchOn.value }
            )
        }
    ) {
        drawRoundRect(
            color = if (switchOn.value)
                checkedTrackColor
            else
                uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
        )

        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchPreview() {
    Switch(onSwitched = {_ ->})
}