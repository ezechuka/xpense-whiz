package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javalon.xpensewhiz.presentation.home_screen.Expenses
import com.javalon.xpensewhiz.presentation.ui.theme.InfoBannerBg
import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue3
import com.javalon.xpensewhiz.presentation.ui.theme.blueText
import com.javalon.xpensewhiz.util.spacing

@Composable
fun ExpenseItem(amount: Double, expense: String) {
    val expenseType = getExpenseType(expense)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { }
        ) {
            Icon(
                painter = painterResource(id = expenseType.iconRes),
                contentDescription = "selected_expense",
                tint = MaterialTheme.colors.surface,
                modifier = Modifier
                    .weight(1f)
                    .background(LightBlue3, shape = CircleShape)
                    .align(Alignment.CenterVertically)
                    .padding(12.dp)
            )
        }

        Text(
            text = expenseType.title,
            style = MaterialTheme.typography.subtitle1,
            color = blueText,
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
        )

        Text(
            text = "$amount".currencyFormat(),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.End,
            color = InfoBannerBg,
            modifier = Modifier
                .weight(1f)
        )
    }
}

fun getExpenseType(expense: String): Expenses {
    var result: Expenses = Expenses.FOOD_DRINK
    Expenses.values().forEach {
        if (it.title == expense)
            result = it
    }
    return result
}