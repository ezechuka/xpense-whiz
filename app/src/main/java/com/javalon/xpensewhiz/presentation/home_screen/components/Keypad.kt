package com.javalon.xpensewhiz.presentation.home_screen.components

import android.view.MotionEvent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.presentation.ui.theme.GridBorderGray
import com.javalon.xpensewhiz.presentation.ui.theme.GridButtonWhite
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun KeypadComponent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onKeyClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        val (keyNine, keyEight, keySeven, keyClear) = createRefs()
        val (keyFour, keyFive, keySix, keyEnter) = createRefs()
        val (keyOne, keyTwo, keyThree) = createRefs()
        val (keyZero, keyPoint) = createRefs()
        val keyWidth = 0.2205f
        val keyAspectRatio = 1f
        val keyGridAlpha = 0.85f
        val keyScale = 0.9f

        val selectedButton7 = remember { mutableStateOf(false) }
        val scale7 = animateFloatAsState(
            if (selectedButton7.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton8 = remember { mutableStateOf(false) }
        val scale8 = animateFloatAsState(
            if (selectedButton8.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton9 = remember { mutableStateOf(false) }
        val scale9 = animateFloatAsState(
            if (selectedButton9.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButtonClr = remember { mutableStateOf(false) }
        val scaleClr = animateFloatAsState(
            if (selectedButtonClr.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton4 = remember { mutableStateOf(false) }
        val scale4 = animateFloatAsState(
            if (selectedButton4.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton5 = remember { mutableStateOf(false) }
        val scale5 = animateFloatAsState(
            if (selectedButton5.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton6 = remember { mutableStateOf(false) }
        val scale6 = animateFloatAsState(
            if (selectedButton6.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButtonEnter = remember { mutableStateOf(false) }
        val scaleEnter = animateFloatAsState(
            if (selectedButtonEnter.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton1 = remember { mutableStateOf(false) }
        val scale1 = animateFloatAsState(
            if (selectedButton1.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton2 = remember { mutableStateOf(false) }
        val scale2 = animateFloatAsState(
            if (selectedButton2.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton3 = remember { mutableStateOf(false) }
        val scale3 = animateFloatAsState(
            if (selectedButton3.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButton0 = remember { mutableStateOf(false) }
        val scale0 = animateFloatAsState(
            if (selectedButton0.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        val selectedButtonPoint = remember { mutableStateOf(false) }
        val scalePoint = animateFloatAsState(
            if (selectedButtonPoint.value) keyScale else 1f,
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )

        // First Row
        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale7.value)
                .constrainAs(keySeven) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton7.value = true
                            onKeyClick("7")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton7.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "7",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale8.value)
                .constrainAs(keyEight) {
                    start.linkTo(keySeven.end)
                    top.linkTo(parent.top)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton8.value = true
                            onKeyClick("8")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton8.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "8",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale9.value)
                .constrainAs(keyNine) {
                    start.linkTo(keyEight.end)
                    top.linkTo(parent.top)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton9.value = true
                            onKeyClick("9")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton9.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "9",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scaleClr.value)
                .constrainAs(keyClear) {
                    start.linkTo(keyNine.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(keyNine.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButtonClr.value = true
                            homeViewModel.backspace()
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButtonClr.value = false
                        }
                    }
                    true
                },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(GridButtonWhite),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = 0.85f))
        ) {
            Icon(
                painter = painterResource(R.drawable.clear),
                contentDescription = "clear",
                tint = Color.DarkGray.copy(alpha = 0.75f)
            )
        }

        // Second Row
        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale4.value)
                .constrainAs(keyFour) {
                    top.linkTo(keySeven.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton4.value = true
                            onKeyClick("4")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton4.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "4",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale5.value)
                .constrainAs(keyFive) {
                    top.linkTo(keyEight.bottom)
                    start.linkTo(keyFour.end)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton5.value = true
                            onKeyClick("5")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton5.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "5",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale6.value)
                .constrainAs(keySix) {
                    top.linkTo(keyNine.bottom)
                    start.linkTo(keyFive.end)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton6.value = true
                            onKeyClick("6")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton6.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "6",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scaleEnter.value)
                .constrainAs(keyEnter) {
                    start.linkTo(keySix.end)
                    top.linkTo(keyClear.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButtonEnter.value = true
                            homeViewModel.apply {
                                scope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButtonEnter.value = false
                        }
                    }
                    true
                },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = 0.85f))
        ) {
            Icon(
                painter = painterResource(R.drawable.collapse),
                contentDescription = "enter",
                tint = Color.White.copy(alpha = 0.95f)
            )
        }

        // Third Row
        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale1.value)
                .constrainAs(keyOne) {
                    top.linkTo(keyFour.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton1.value = true
                            onKeyClick("1")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton1.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "1",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale2.value)
                .constrainAs(keyTwo) {
                    top.linkTo(keyFive.bottom)
                    start.linkTo(keyOne.end)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton2.value = true
                            onKeyClick("2")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton2.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "2",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale3.value)
                .constrainAs(keyThree) {
                    top.linkTo(keySix.bottom)
                    start.linkTo(keyTwo.end)
                    width = Dimension.percent(keyWidth)
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton3.value = true
                            onKeyClick("3")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton3.value = false
                        }
                    }
                    true
                }
                .aspectRatio(keyAspectRatio),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "3",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        // Fourth/Last row
        Button(
            onClick = { },
            modifier = Modifier
                .scale(scale0.value)
                .constrainAs(keyZero) {
                    top.linkTo(keyOne.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(keyWidth * 2)
                    height = Dimension.fillToConstraints
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButton0.value = true
                            onKeyClick("0")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButton0.value = false
                        }
                    }
                    true
                },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = "0",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                color = MaterialTheme.colors.onSurface,
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .scale(scalePoint.value)
                .constrainAs(keyPoint) {
                    top.linkTo(keyThree.bottom)
                    start.linkTo(keyZero.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.percent(keyWidth)
                    height = Dimension.fillToConstraints
                }
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selectedButtonPoint.value = true
                            onKeyClick(".")
                        }

                        MotionEvent.ACTION_UP -> {
                            selectedButtonPoint.value = false
                        }
                    }
                    true
                },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.5.dp
            ),
            border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = keyGridAlpha))
        ) {
            Text(
                text = ".",
                style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface,
            )
        }

    }
}

@ExperimentalUnitApi
@Composable
fun HelperButton(modifier: Modifier = Modifier, text: Int, onClick: (Int) -> Unit) {
    Button(
        onClick = { onClick(text) },
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(GridButtonWhite),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.5.dp),
        border = BorderStroke(0.25.dp, GridBorderGray.copy(alpha = 0.3f))
    ) {
        Text(
            text = "+$text",
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 17.sp),
            color = MaterialTheme.colors.onSurface,
            letterSpacing = TextUnit(0.2f, TextUnitType.Sp)
        )
    }
}