package com.javalon.xpensewhiz.presentation.insight_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.Category
import com.javalon.xpensewhiz.presentation.home_screen.amountFormat
import com.javalon.xpensewhiz.presentation.home_screen.components.ListPlaceholder
import com.javalon.xpensewhiz.presentation.insight_screen.components.DonutChart
import com.javalon.xpensewhiz.presentation.insight_screen.components.InsightItem
import com.javalon.xpensewhiz.presentation.insight_screen.components.InsightTabBar
import com.javalon.xpensewhiz.util.spacing

@ExperimentalFoundationApi
@ExperimentalUnitApi
@Composable
fun InsightScreen(insightViewModel: InsightViewModel = hiltViewModel()) {

    val filteredTransactions by insightViewModel.filteredTransaction.collectAsState()
    val currencyCode by insightViewModel.selectedCurrencyCode.collectAsState()

    val total = filteredTransactions.sumOf { it.amount }

    val groupedData = filteredTransactions.groupBy {
        it.category
    }

    val filteredCategories = mutableListOf<Category>()
    groupedData.forEach { data ->
        Category.values().forEach cat@{
            if (data.key == it.title) {
                filteredCategories.add(it)
                return@cat
            }
        }
    }

    val amountList = groupedData.map {
        it.value.sumOf { trx ->
            trx.amount
        }
    }
    val totalTrx = amountList.map { it.toFloat() }.sum()
    val percentProgress = amountList.map {
        it.toFloat() * 100 / totalTrx
    }

    var expandedState by remember { mutableStateOf(false) }
    val limitDuration by remember {
        mutableStateOf(
            listOf(
                "Last 3 Days", "Last 7 Days", "Last 14 Days", "This Month",
                "Last Month", "All"
            )
        )
    }
    var selectedDuration by remember { mutableStateOf(limitDuration.last()) }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium,
            top = MaterialTheme.spacing.small
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        expandedState = !expandedState
                    }
                    .padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDuration,
                    style = MaterialTheme.typography.subtitle1
                )

                Icon(
                    painter = painterResource(R.drawable.pop_up),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )

                DropdownMenu(
                    expanded = expandedState,
                    onDismissRequest = { expandedState = false }
                ) {
                    limitDuration.forEachIndexed { index, label ->
                        DropdownMenuItem(onClick = {
                            selectedDuration = label
                            insightViewModel.getFilteredTransaction(index)
                            expandedState = false
                        }) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.subtitle2,
                                color = if (selectedDuration == label)
                                    MaterialTheme.colors.primary
                                else
                                    Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            InsightTabBar()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                letterSpacing = TextUnit(1.1f, TextUnitType.Sp),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Text(
                text = currencyCode + total.toString().amountFormat(),
                style = MaterialTheme.typography.h3.copy(fontSize = 20.sp),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            LazyColumn {
                item {
                    if (filteredTransactions.isNotEmpty())
                        DonutChart(filteredCategories, percentProgress)
                }

                itemsIndexed(filteredCategories) { index, category ->
                    val amount = groupedData[category.title]?.sumOf { it.amount }
                    InsightItem(cat = category, currencyCode, amount = amount!!, percentProgress[index])
                }
            }

            filteredTransactions.ifEmpty {
                ListPlaceholder(
                    "No transaction. Tap the '+' button on the home menu to get started."
                )
            }
        }
    }
}