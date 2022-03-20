package com.javalon.xpensewhiz.presentation.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.components.AddEntryComponent
import com.javalon.xpensewhiz.presentation.home_screen.components.ExpenseItem
import com.javalon.xpensewhiz.presentation.home_screen.components.Header
import com.javalon.xpensewhiz.presentation.home_screen.components.TabButton
import com.javalon.xpensewhiz.presentation.home_screen.components.currencyFormat
import com.javalon.xpensewhiz.presentation.ui.theme.GreenAlpha700
import com.javalon.xpensewhiz.util.spacing

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    // expense entries
    val dailyExpenses = homeViewModel.dailyExpense.value
    val monthlyExpenses = homeViewModel.monthlyExpense.value

    val lazyListState = rememberLazyListState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = { AddEntryComponent(bottomSheetScaffoldState) },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // header
            Header(bottomSheetScaffoldState, lazyListState)

            // Button tabs
            TabButton()

            // daily expenses
            AnimatedVisibility(visible = homeViewModel.tabButton.value == TabButton.TODAY) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.large
                    )
                ) {
                    stickyHeader {
                        Text(
                            text = homeViewModel.formattedDate.value,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .padding(vertical = MaterialTheme.spacing.small)
                                .fillMaxWidth()
                                .background(
                                    GreenAlpha700.copy(alpha = 0.9f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = MaterialTheme.spacing.medium,
                                    vertical = MaterialTheme.spacing.small
                                ),
                            textAlign = TextAlign.Start
                        )
                    }
                    items(dailyExpenses) { dailyExpenses ->
                        ExpenseItem(
                            amount = dailyExpenses.amount,
                            expense = dailyExpenses.expenseType
                        )
                    }
                }
            }

            // monthly expenses
            AnimatedVisibility(visible = homeViewModel.tabButton.value == TabButton.MONTH) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.large
                    )
                ) {
                    monthlyExpenses.forEach { (date, monthlyExpenses) ->
                        stickyHeader {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(vertical = MaterialTheme.spacing.small)
                                    .fillMaxWidth()
                                    .background(
                                        GreenAlpha700.copy(alpha = 0.9f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(
                                        horizontal = MaterialTheme.spacing.medium,
                                        vertical = MaterialTheme.spacing.small
                                    ),
                            ) {
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White,
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = homeViewModel.calculateExpense(monthlyExpenses)
                                        .toString().currencyFormat(),
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        items(monthlyExpenses) { dailyExpenses ->
                            ExpenseItem(
                                amount = dailyExpenses.amount,
                                expense = dailyExpenses.expenseType
                            )
                        }
                    }
                }
            }


            // blank list container
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.wallet),
                    contentDescription = "emptyList",
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.spacing.small),
                    tint = Color.DarkGray.copy(0.15f)
                )
                Text(
                    text = "No entries today", style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.small
                    ),
                    color = Color.DarkGray.copy(0.5f)
                )
            }
        }
    }
}