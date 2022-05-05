package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.presentation.home_screen.amountFormat
import com.javalon.xpensewhiz.presentation.ui.theme.Amber500
import com.javalon.xpensewhiz.presentation.ui.theme.GreenAlpha700
import com.javalon.xpensewhiz.presentation.ui.theme.Red500
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun Header(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val currentDate by homeViewModel.formattedDate.collectAsState()
    val totalIncome by homeViewModel.totalIncome.collectAsState()
    val totalExpense by homeViewModel.totalExpense.collectAsState()
    val currencyCode by homeViewModel.selectedCurrencyCode.collectAsState()

    val extraSmall = MaterialTheme.spacing.extraSmall
    val small = MaterialTheme.spacing.small
    val medium = MaterialTheme.spacing.medium

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        contentAlignment = Alignment.Center
    ) {

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        Surface(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.small
            ), color = Color.DarkGray.copy(alpha = 0.1f), shape = RoundedCornerShape(24.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (addEntry, balanceLabel, amountLabel) = createRefs()
                val (incomeCard, expenseCard) = createRefs()

                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    },
                    modifier = Modifier.constrainAs(addEntry) {
                        top.linkTo(parent.top, margin = small)
                        end.linkTo(parent.end, margin = medium)
                    },
                    colors = ButtonDefaults.buttonColors(Amber500.copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(24.dp)
                )
                {
                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(8.dp),
                        textAlign = TextAlign.Start
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.add_entry),
                        contentDescription = "add entry",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colors.onSurface,
                                CircleShape
                            )
                            .scale(0.7f)
                    )
                }

                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colors.onSurface,
                    letterSpacing = TextUnit(1.5f, TextUnitType.Sp),
                    modifier = Modifier.constrainAs(balanceLabel) {
                        start.linkTo(parent.start, margin = medium)
                        top.linkTo(addEntry.bottom)
                    }
                )

                val animatedBalance by animateFloatAsState(
                    targetValue = totalIncome.toFloat() - totalExpense.toFloat(),
                    animationSpec = tween(durationMillis = 900)
                )

                Text(
                    text = currencyCode + "$animatedBalance".amountFormat(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.constrainAs(amountLabel) {
                        start.linkTo(parent.start, margin = medium)
                        top.linkTo(balanceLabel.bottom, margin = extraSmall)
                    }
                )

                val mediumIncomeColoredPoint1 = Offset(0f, layoutHeight * 0.3f)
                val mediumIncomeColoredPoint2 = Offset(layoutWidth * 0.1f, layoutHeight * 0.35f)
                val mediumIncomeColoredPoint3 = Offset(layoutWidth * 0.4f, layoutHeight * 0.05f)
                val mediumIncomeColoredPoint4 = Offset(layoutWidth * 0.75f, layoutHeight * 0.85f)
                val mediumIncomeColoredPoint5 = Offset(layoutWidth * 1.4f, -layoutHeight.toFloat())

                val mediumExpenseColoredPoint1 = Offset(0f, layoutHeight * 0.2f)
                val mediumExpenseColoredPoint2 = Offset(layoutWidth * 0.2f, layoutHeight * 0.4f)
                val mediumExpenseColoredPoint3 = Offset(layoutWidth * 0.45f, layoutHeight * 0.2f)
                val mediumExpenseColoredPoint4 = Offset(layoutWidth * 0.75f, layoutHeight * 0.85f)
                val mediumExpenseColoredPoint5 = Offset(layoutWidth * 1.4f, -layoutHeight.toFloat())

                val mediumIncomeColoredPath = Path().apply {
                    moveTo(mediumIncomeColoredPoint1.x, mediumIncomeColoredPoint1.y)
                    standardQuadFromTo(mediumIncomeColoredPoint1, mediumIncomeColoredPoint2)
                    standardQuadFromTo(mediumIncomeColoredPoint2, mediumIncomeColoredPoint3)
                    standardQuadFromTo(mediumIncomeColoredPoint3, mediumIncomeColoredPoint4)
                    standardQuadFromTo(mediumIncomeColoredPoint4, mediumIncomeColoredPoint5)
                    lineTo(layoutWidth.toFloat(), layoutHeight.toFloat())
                    lineTo(-100f, layoutHeight.toFloat())
                    close()
                }

                val mediumExpenseColoredPath = Path().apply {
                    moveTo(mediumExpenseColoredPoint1.x, mediumExpenseColoredPoint1.y)
                    standardQuadFromTo(mediumExpenseColoredPoint1, mediumExpenseColoredPoint2)
                    standardQuadFromTo(mediumExpenseColoredPoint2, mediumExpenseColoredPoint3)
                    standardQuadFromTo(mediumExpenseColoredPoint3, mediumExpenseColoredPoint4)
                    standardQuadFromTo(mediumExpenseColoredPoint4, mediumExpenseColoredPoint5)
                    lineTo(layoutWidth.toFloat(), layoutHeight.toFloat())
                    lineTo(-100f, layoutHeight.toFloat())
                    close()
                }

                val lightIncomePoint1 = Offset(0f, layoutHeight * 0.4f)
                val lightIncomePoint2 = Offset(layoutWidth * 0.1f, layoutHeight * 0.4f + 45f)
                val lightIncomePoint3 = Offset(layoutWidth * 0.4f, layoutHeight * 0.35f)
                val lightIncomePoint4 = Offset(layoutWidth * 0.75f, layoutHeight.toFloat() + 45f)
                val lightIncomePoint5 = Offset(layoutWidth * 1.4f, -layoutHeight.toFloat() / 3f)

                val lightExpensePoint1 = Offset(0f, layoutHeight * 0.9f)
                val lightExpensePoint2 = Offset(layoutWidth * 0.001f, layoutHeight * 0.4f + 45f)
                val lightExpensePoint3 = Offset(layoutWidth * 0.4f, layoutHeight * 0.35f)
                val lightExpensePoint4 = Offset(layoutWidth * 0.75f, layoutHeight.toFloat() + 45f)
                val lightExpensePoint5 = Offset(layoutWidth * 1.4f, -layoutHeight.toFloat() / 3f)

                val lightIncomeColoredPath = Path().apply {
                    moveTo(lightIncomePoint1.x, lightIncomePoint1.y)
                    standardQuadFromTo(lightIncomePoint1, lightIncomePoint2)
                    standardQuadFromTo(lightIncomePoint2, lightIncomePoint3)
                    standardQuadFromTo(lightIncomePoint3, lightIncomePoint4)
                    standardQuadFromTo(lightIncomePoint4, lightIncomePoint5)
                    lineTo(layoutWidth.toFloat() + 10f, layoutHeight.toFloat())
                    lineTo(-10f, layoutHeight.toFloat())
                    close()
                }

                val lightExpenseColoredPath = Path().apply {
                    moveTo(lightIncomePoint1.x, lightIncomePoint1.y)
                    standardQuadFromTo(lightExpensePoint1, lightExpensePoint2)
                    standardQuadFromTo(lightExpensePoint2, lightExpensePoint3)
                    standardQuadFromTo(lightExpensePoint3, lightExpensePoint4)
                    standardQuadFromTo(lightExpensePoint4, lightExpensePoint5)
                    lineTo(layoutWidth.toFloat() + 10f, layoutHeight.toFloat())
                    lineTo(-10f, layoutHeight.toFloat())
                    close()
                }

                Card(
                    elevation = 16.dp,
                    backgroundColor = GreenAlpha700,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .constrainAs(incomeCard) {
                            top.linkTo(amountLabel.bottom, margin = small)
                            start.linkTo(parent.start, margin = medium)
                            bottom.linkTo(parent.bottom, margin = small)
                            width = Dimension.percent(0.42f)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                drawPath(
                                    mediumIncomeColoredPath,
                                    color = Color.LightGray.copy(alpha = 0.40f)
                                )
                                drawPath(
                                    lightIncomeColoredPath,
                                    color = Color.LightGray.copy(alpha = 0.35f)
                                )
                            }
                            .padding(MaterialTheme.spacing.small)
                    ) {
                        val animatedIncome by animateFloatAsState(
                            targetValue = totalIncome.toFloat(),
                            animationSpec = tween(durationMillis = 900)
                        )

                        val (incomeIcon, incomeLabel, code, incomeAmount) = createRefs()
                        Icon(
                            painter = painterResource(id = R.drawable.income),
                            contentDescription = "Income",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .constrainAs(incomeIcon) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                }
                                .then(Modifier.size(24.dp))
                        )
                        Text(
                            text = "Income",
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(incomeLabel) {
                                top.linkTo(incomeIcon.bottom, margin = extraSmall)
                                start.linkTo(parent.start)
                            }
                        )
                        Text(
                            text = currencyCode,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(code) {
                                start.linkTo(parent.start)
                                bottom.linkTo(incomeAmount.top)
                            }
                        )
                        Text(
                            text = "$animatedIncome".amountFormat().trim(),
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(incomeAmount) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            }
                        )
                    }

                }

                Card(
                    elevation = 16.dp,
                    backgroundColor = Red500,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .constrainAs(expenseCard) {
                            top.linkTo(amountLabel.bottom, margin = small)
                            end.linkTo(parent.end, margin = medium)
                            bottom.linkTo(parent.bottom, margin = small)
                            width = Dimension.percent(0.42f)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawBehind {
                                drawPath(
                                    mediumExpenseColoredPath,
                                    color = Color.LightGray.copy(alpha = 0.40f)
                                )
                                drawPath(
                                    lightExpenseColoredPath,
                                    color = Color.LightGray.copy(alpha = 0.35f)
                                )
                            }
                            .padding(MaterialTheme.spacing.small)
                    ) {
                        val animatedExpense by animateFloatAsState(
                            targetValue = totalExpense.toFloat(),
                            animationSpec = tween(durationMillis = 900)
                        )

                        val (expenseIcon, expenseLabel, code, expenseAmount) = createRefs()
                        Icon(
                            painter = painterResource(id = R.drawable.expense),
                            contentDescription = "Expense",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .constrainAs(expenseIcon) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                }
                                .then(Modifier.size(24.dp))
                        )
                        Text(
                            text = "Expense",
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(expenseLabel) {
                                top.linkTo(expenseIcon.bottom, margin = extraSmall)
                                start.linkTo(parent.start)
                            }
                        )
                        Text(
                            text = currencyCode,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(code) {
                                start.linkTo(parent.start)
                                bottom.linkTo(expenseAmount.top)
                            }
                        )
                        Text(
                            text = "$animatedExpense".amountFormat().trim(),
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier.constrainAs(expenseAmount) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            }
                        )
                    }
                }

            }
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