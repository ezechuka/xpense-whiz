package com.javalon.xpensewhiz.presentation.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.javalon.xpensewhiz.presentation.home_screen.components.AddEntryChooser
import com.javalon.xpensewhiz.presentation.home_screen.components.Header
import com.javalon.xpensewhiz.presentation.home_screen.components.ListPlaceholder
import com.javalon.xpensewhiz.presentation.home_screen.components.TabButton
import com.javalon.xpensewhiz.presentation.home_screen.components.TransactionItem
import com.javalon.xpensewhiz.presentation.navigation.Screen
import com.javalon.xpensewhiz.util.spacing

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    // expense entries
    val dailyTransactions by homeViewModel.dailyTransaction.collectAsState()
    val monthlyTransactions by homeViewModel.monthlyTransaction.collectAsState()
    val currentTabButton by homeViewModel.tabButton.collectAsState()

    val lazyListState = rememberLazyListState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        sheetContent = { AddEntryChooser(bottomSheetScaffoldState, navController) },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // header
            Header(bottomSheetScaffoldState)

            // Button tabs
            TabButton()

            // daily expenses

            AnimatedVisibility(visible = currentTabButton == TabButton.TODAY) {
                dailyTransactions.ifEmpty {
                    ListPlaceholder()
                }
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.medium
                    )
                ) {
                    itemsIndexed(dailyTransactions) { pos, dailyTransaction ->
                        TransactionItem(
                            transaction = dailyTransaction,
                            onItemClick = {
                                val trxType = dailyTransaction.transactionType
                                if (trxType == TransactionType.INCOME.title)
                                    navController.navigate(
                                        "${Screen.TransactionScreen.route}/0?trxPos=${pos}&trxStatus=${0}"
                                    )
                                else
                                    navController.navigate(
                                        "${Screen.TransactionScreen.route}/1?trxPos=${pos}&trxStatus=${0}"
                                    )
                            }
                        )
                    }
                }
            }

            // monthly expenses
            AnimatedVisibility(visible = currentTabButton == TabButton.MONTH) {
                monthlyTransactions.ifEmpty {
                    ListPlaceholder()
                }
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.spacing.medium,
                        top = MaterialTheme.spacing.small,
                        end = MaterialTheme.spacing.medium
                    )
                ) {
                    monthlyTransactions.forEach { (date, monthlyTransaction) ->
                        stickyHeader {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(
                                        horizontal = MaterialTheme.spacing.medium,
                                        vertical = MaterialTheme.spacing.small
                                    ),
                            ) {
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.onBackground,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        itemsIndexed(monthlyTransaction) { pos, transaction ->
                            TransactionItem(
                                transaction = transaction,
                                onItemClick = {
                                    val trxType = transaction.transactionType
                                    if (trxType == TransactionType.INCOME.title)
                                        navController.navigate(
                                            "${Screen.TransactionScreen.route}/0?trxKey=${date}&trxPos=${pos}&trxStatus=${1}"
                                        )
                                    else
                                        navController.navigate(
                                            "${Screen.TransactionScreen.route}/1?trxKey=${date}&trxPos=${pos}&trxStatus=${1}"
                                        )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}