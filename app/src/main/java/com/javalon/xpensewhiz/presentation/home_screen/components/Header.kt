package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue1
import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue2
import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue3
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.min

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun Header(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    lazyListState: LazyListState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val extraSmall = MaterialTheme.spacing.extraSmall
    val small = MaterialTheme.spacing.small
    val medium = MaterialTheme.spacing.medium
    val large = MaterialTheme.spacing.large

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
//            .graphicsLayer {
//                alpha = min(1f, 1 - (lazyListState.firstVisibleItemScrollOffset / 600f))
//                translationY = -lazyListState.firstVisibleItemScrollOffset * 0.1f
//            }
        ,
        contentAlignment = Alignment.BottomCenter
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.85f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat(), height.toFloat())
            lineTo(-100f, height.toFloat())
            close()
        }

        val lightPoint1 = Offset(0f, height * 0.4f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f + 45f)
        val lightPoint3 = Offset(width * 0.4f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.75f, height.toFloat() + 45f)
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 10f, height.toFloat())
            lineTo(-10f, height.toFloat())
            close()
        }

        ConstraintLayout(modifier = Modifier
            .background(LightBlue3.copy(alpha = 0.95f))
            .drawBehind {
                drawPath(mediumColoredPath, color = LightBlue2.copy(alpha = 0.35f))
                drawPath(lightColoredPath, color = LightBlue1.copy(alpha = 0.25f))
            }
            .fillMaxSize())
        {
            val (addEntry, infoLabel, amountLabel) = createRefs()
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                },
                modifier = Modifier.constrainAs(addEntry) {
                    top.linkTo(parent.top, margin = medium)
                    end.linkTo(parent.end, margin = small)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_entry),
                    contentDescription = "add entry",
                    tint = MaterialTheme.colors.background
                )
            }

            Text(
                text = "spent this month",
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colors.background,
                letterSpacing = TextUnit(1.5f, TextUnitType.Sp),
                modifier = Modifier.constrainAs(infoLabel) {
                    start.linkTo(parent.start, margin = large)
                    bottom.linkTo(amountLabel.top)
                }
            )

            val animatedAmount by animateFloatAsState(
                targetValue = homeViewModel.totalAmountPerDay.value.toFloat(),
                animationSpec = tween(durationMillis = 900)
            )
            Text(
                text = "$animatedAmount".currencyFormat(),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.background,
                modifier = Modifier.constrainAs(amountLabel) {
                    start.linkTo(parent.start, margin = large)
                    bottom.linkTo(parent.bottom, margin = extraSmall)
                }
            )
        }
    }
}

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x, from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}

fun String.currencyFormat(): String {
    val currencyFormatter = NumberFormat.getCurrencyInstance()
    currencyFormatter.maximumFractionDigits = 2
    currencyFormatter.currency = Currency.getInstance("EUR")
    return currencyFormatter.format(this.toDouble())
}