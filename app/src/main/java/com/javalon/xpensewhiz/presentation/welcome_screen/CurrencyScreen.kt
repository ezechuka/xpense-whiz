package com.javalon.xpensewhiz.presentation.welcome_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.javalon.xpensewhiz.presentation.navigation.Screen
import com.javalon.xpensewhiz.presentation.ui.theme.GreenAlpha700
import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue3
import com.javalon.xpensewhiz.presentation.ui.theme.Manrope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalUnitApi
@ExperimentalFoundationApi
@Composable
fun CurrencyScreen(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val currencies by welcomeViewModel.countryCurrencies
    var selectedCountry by remember { mutableStateOf(String()) }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = {
            ContinueButton(
                navController = navController,
                welcomeViewModel = welcomeViewModel
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Card(elevation = 1.dp) {
                Text(
                    text = "Set currency",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    letterSpacing = TextUnit(0.2f, TextUnitType.Sp),
                    textAlign = TextAlign.Start
                )
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = it.calculateBottomPadding())
            ) {
                currencies.forEach { (firstChar, list) ->
                    stickyHeader {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = firstChar.toString(),
                                style = MaterialTheme.typography.subtitle2,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    items(list) { currency ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    selectedCountry = if (selectedCountry != currency.country) {
                                        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                                        currency.country
                                    } else {
                                        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                                        String()
                                    }
                                }
                                .fillMaxWidth()
                                .background(
                                    if (selectedCountry == currency.country)
                                        LightBlue3
                                    else MaterialTheme.colors.surface
                                )
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = if (selectedCountry == currency.country)
                                                MaterialTheme.colors.surface
                                            else MaterialTheme.colors.onSurface,
                                            fontWeight = FontWeight.W600,
                                            fontFamily = Manrope,
                                            fontSize = 14.sp
                                        )
                                    ) {
                                        append(currency.country.uppercase())
                                    }

                                    withStyle(
                                        style = SpanStyle(
                                            color = Color.DarkGray.copy(alpha = 0.5f),
                                            fontWeight = FontWeight.Normal,
                                            fontFamily = Manrope,
                                            fontSize = 14.sp
                                        )
                                    ) {
                                        append(" (${currency.currencyCode})")
                                    }
                                },
                                style = MaterialTheme.typography.subtitle2,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContinueButton(navController: NavController, welcomeViewModel: WelcomeViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {
                navController.popBackStack()
                welcomeViewModel.saveOnBoardingState(completed = true)
                navController.navigate(Screen.HomeScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = GreenAlpha700)
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}