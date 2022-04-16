package com.javalon.xpensewhiz.presentation.setting_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.presentation.home_screen.amountFormat
import com.javalon.xpensewhiz.presentation.setting_screen.SettingViewModel
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun LimitSetting(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val currencyCode by settingViewModel.currency.collectAsState()
    val expenseLimit by settingViewModel.expenseLimit.collectAsState()

    TextButton(
        onClick = {
            onItemClick(1)
            scope.launch {
                modalBottomSheetState.show()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.medium,
            vertical = 20.dp
        )
    ) {
        Text(
            text = "Expense Limit",
            style = MaterialTheme.typography.button,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Start
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "$currencyCode " + expenseLimit.toString().amountFormat(),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}