package com.javalon.xpensewhiz.presentation.insight_screen.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.javalon.xpensewhiz.presentation.home_screen.Category
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun DonutChart(
    filteredCategories: MutableList<Category>,
    percentProgress: List<Float>
) {
    val angleProgress = percentProgress.map {
        360 * it / 100f
    }

    val progressSize = mutableListOf(angleProgress.first())
    for (x in 1 until angleProgress.size) {
        progressSize.add(angleProgress[x] + progressSize[x - 1])
    }

    val arcPortion = remember(filteredCategories) {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = filteredCategories) {
        arcPortion.animateTo(
            1f,
            animationSpec = spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessLow)
        )
    }

    var startAngle = 270f
    var activeArc by remember (filteredCategories) { mutableStateOf(-1) }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        val sideSize = min(constraints.maxWidth, constraints.maxHeight)
        val padding = sideSize * 55 / 100f

        val size = Size(sideSize.toFloat() - padding, sideSize.toFloat() - padding)

        val donutFont = Typeface.createFromAsset(
            LocalContext.current.assets,
            "manrope_medium.otf"
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .pointerInput(filteredCategories) {
                    detectTapGestures {
                        val clickedAngle = convertTouchEventPointToAngle(
                            sideSize.toFloat(),
                            sideSize.toFloat(),
                            it.x,
                            it.y
                        )

                        progressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                if (activeArc != index)
                                    activeArc = index

                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {

            angleProgress.forEachIndexed { index, progress ->
                DrawDonutArc(
                    cat = filteredCategories[index],
                    startAngle = startAngle,
                    sweepAngle = arcPortion.value * (progress - 1f),
                    size = size,
                    padding = padding,
                    isActive = activeArc == index
                )
                startAngle += progress
            }

            if (activeArc != -1)
                drawContext.canvas.nativeCanvas.apply {
                    val fontSize = 80.toDp().toPx()
                    drawText(
                        "${percentProgress[activeArc].roundToInt()}%",
                        (sideSize / 2) + fontSize / 4, (sideSize / 3) + fontSize / 2,
                        Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = fontSize
                            textAlign = Paint.Align.CENTER
                            typeface = Typeface.create(donutFont, Typeface.BOLD)
                        }
                    )
                }
        }
    }
}

fun DrawScope.DrawDonutArc(
    cat: Category,
    startAngle: Float,
    sweepAngle: Float,
    size: Size,
    padding: Float,
    isActive: Boolean = false
) {
    drawArc(
        color = cat.bgRes,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = size,
        topLeft = Offset(
            padding / 2f, padding / 4f
        ),
        style = Stroke(
            width = if (isActive) 160f else 120f
        )
    )
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    val x = xPos - (width * 0.5f)
    val y = yPos - (height * 0.5f)

    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}