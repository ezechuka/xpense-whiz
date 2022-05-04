package com.javalon.xpensewhiz.presentation.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.presentation.home_screen.components.AccountTag
import com.javalon.xpensewhiz.presentation.home_screen.components.Category
import com.javalon.xpensewhiz.presentation.home_screen.components.InfoBanner
import com.javalon.xpensewhiz.presentation.home_screen.components.KeypadComponent
import com.javalon.xpensewhiz.presentation.ui.theme.Amber500
import com.javalon.xpensewhiz.presentation.ui.theme.Red200
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.launch
import java.util.Calendar

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun TransactionScreen(
    navController: NavController,
    transactionTag: Int?,
    transactionDate: String?,
    transactionPos: Int?,
    transactionStatus: Int?,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val transactionType = TransactionType.values()[transactionTag!!]
    val scope = rememberCoroutineScope()
    val keypadBottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val title by remember { mutableStateOf(homeViewModel.transactionTitle) }
    val titleFieldValue = TextFieldValue(title.collectAsState().value)
    val showInfoBanner by homeViewModel.showInfoBanner.collectAsState()
    val expenseAmount by homeViewModel.transactionAmount.collectAsState()
    val currencyCode by homeViewModel.selectedCurrencyCode.collectAsState()
    val limitKey by homeViewModel.limitKey.collectAsState()
    val limitInfoWarning by homeViewModel.limitAlert.collectAsState(initial = HomeViewModel.UIEvent.NoAlert())

    BottomSheetScaffold(
        sheetContent = {
            KeypadComponent(
                bottomSheetScaffoldState = keypadBottomSheetState
            ) {
                homeViewModel.setTransaction(it)
            }
        },
        scaffoldState = keypadBottomSheetState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = MaterialTheme.colors.background
    ) {
        LaunchedEffect(key1 = transactionPos) {
            if (transactionPos != -1) {
                homeViewModel.displayTransaction(transactionDate, transactionPos, transactionStatus)
            }
            homeViewModel.displayExpenseLimitWarning()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium,
                            top = MaterialTheme.spacing.small
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Transaction",
                        modifier = Modifier.weight(2f),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface
                    )

                    IconButton(
                        onClick = {
                            homeViewModel.apply {
                                if (transactionPos == -1) {
                                    setCurrentTime(Calendar.getInstance().time)
                                    if (transactionType == TransactionType.INCOME) {
                                        insertDailyTransaction(
                                            date.value,
                                            transactionAmount.value.toDouble(),
                                            category.value.title,
                                            Constants.INCOME, transactionTitle.value
                                        ) {
                                            navController.navigateUp()
                                        }
                                    } else {
                                        insertDailyTransaction(
                                            date.value,
                                            transactionAmount.value.toDouble(),
                                            category.value.title,
                                            Constants.EXPENSE, transactionTitle.value
                                        ) {
                                            navController.navigateUp()
                                        }
                                    }
                                } else {
                                    updateTransaction(
                                        transactionDate,
                                        transactionPos,
                                        transactionStatus
                                    ) {
                                        navController.navigateUp()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .scale(0.8f)
                            .background(MaterialTheme.colors.primary, CircleShape)

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.enter),
                            contentDescription = "enter",
                            tint = MaterialTheme.colors.surface,
                            modifier = Modifier
                                .scale(0.8f)
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .scale(0.75f)
                            .border(1.dp, MaterialTheme.colors.onSurface, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.remove),
                            contentDescription = "close",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .scale(0.8f)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoBanner(shown = showInfoBanner, transactionType)

                    TextField(
                        value = titleFieldValue.text,
                        onValueChange = { field ->
                            homeViewModel.setTransactionTitle(field)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.spacing.medium,
                                top = MaterialTheme.spacing.small,
                                end = MaterialTheme.spacing.medium,
                                bottom = MaterialTheme.spacing.medium
                            ),
                        maxLines = 1,
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = if (transactionType == TransactionType.INCOME)
                                    "Income title"
                                else "Expense title",
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.W600)
                            )
                        },
                        textStyle = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.W600),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colors.primary,
                            cursorColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.LightGray
                        )
                    )

                    Text(
                        text = if (transactionType == TransactionType.INCOME) {
                            "Fund"
                        } else "Pay with",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.small
                            )
                            .align(Alignment.Start)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.medium
                            )
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.small
                            )
                            .align(Alignment.Start)
                    ) {
                        items(Account.values()) { account ->
                            AccountTag(account = account)
                        }
                    }

                    TextButton(
                        onClick = {
                            scope.launch {
                                keyboardController?.hide()
                                if (keypadBottomSheetState.bottomSheetState.isCollapsed)
                                    keypadBottomSheetState.bottomSheetState.expand()
                                else keypadBottomSheetState.bottomSheetState.collapse()
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                start = MaterialTheme.spacing.medium,
                                top = MaterialTheme.spacing.small
                            ),
                        colors = ButtonDefaults.textButtonColors(Amber500.copy(alpha = 0.8f)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.small
                        ),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W300,
                                        fontSize = 24.sp
                                    )
                                ) {
                                    append(currencyCode)
                                    append(expenseAmount.amountFormat())
                                }
                            },
                            color = MaterialTheme.colors.surface,
                        )
                    }
                    if (limitKey) {
                        if (limitInfoWarning is HomeViewModel.UIEvent.Alert) {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .padding(
                                        horizontal = MaterialTheme.spacing.medium
                                    )
                                    .align(Alignment.Start)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.info_warning),
                                    contentDescription = null,
                                    tint = Red200
                                )
                                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                                    Text(
                                        text = (limitInfoWarning as HomeViewModel.UIEvent.Alert).info,
                                        style = MaterialTheme.typography.caption
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                    Text(
                        text = "Set category",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onSurface,
                        letterSpacing = TextUnit(0.2f, TextUnitType.Sp),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.small
                            )
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = MaterialTheme.spacing.medium
                            )
                    )

                    Category()
                }
            }
        }
    }
}