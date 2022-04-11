package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.Account
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.util.spacing

@ExperimentalUnitApi
@Composable
fun AccountTag(account: Account, homeViewModel: HomeViewModel = hiltViewModel()) {
    val selectedAccount by homeViewModel.account.collectAsState()
    val isSelected = selectedAccount == account

    TextButton(
        onClick = {
            homeViewModel.selectAccount(account)
        },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected)
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            else Color.Transparent,
            contentColor = if (isSelected)
                Color.White else
                MaterialTheme.colors.primary
        ),
    ) {
        Icon(
            painter = painterResource(
                id = if (isSelected)
                    R.drawable.checked else account.iconRes
            ),
            contentDescription = account.title,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = account.title,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Bold,
            letterSpacing = TextUnit(1.1f, TextUnitType.Sp)
        )
    }
}

@ExperimentalUnitApi
@Preview(showBackground = true)
@Composable
fun AccountTagPreview() {
    AccountTag(account = Account.CARD)
}