package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javalon.xpensewhiz.presentation.home_screen.Expenses

@ExperimentalFoundationApi
@Composable
fun CategoryDialog(
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit,
    expenseItems: Array<Expenses> = Expenses.values()
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .fillMaxHeight(0.8f),
            onDismissRequest = { onDismiss(showDialog) },
            title = null,
            text = null,
            buttons = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Expenses",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp)
                    )

                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.Center,
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 8.dp))
                    {
                        items(expenseItems) { expenseItem ->
                            CategoryItem(item = expenseItem)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(32.dp)
        )
    }
}