package com.javalon.xpensewhiz.presentation.account_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.javalon.xpensewhiz.presentation.account_screen.components.AccountItem
import com.javalon.xpensewhiz.presentation.navigation.Screen
import com.javalon.xpensewhiz.util.spacing

@ExperimentalMaterialApi
@Composable
fun AccountScreen(
    navController: NavHostController,
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val accounts by accountViewModel.allAccounts.collectAsState()
    val currency by accountViewModel.selectedCurrencyCode.collectAsState()
    Surface(
        color = MaterialTheme.colors.background
    ) {
        LazyColumn {
            item {
                Text(
                    text = "Accounts",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W700),
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.medium,
                            end = MaterialTheme.spacing.medium,
                            top = MaterialTheme.spacing.small
                        ),
                    textAlign = TextAlign.Start
                )

            }
            items(accounts) { account ->
                AccountItem(account, currency) { accountType ->
                    navController.navigate("${Screen.AccountDetailScreen.route}/$accountType")
                }
            }
        }
    }
}