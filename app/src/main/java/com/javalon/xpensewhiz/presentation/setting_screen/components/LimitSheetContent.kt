package com.javalon.xpensewhiz.presentation.setting_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.setting_screen.SettingViewModel
import com.javalon.xpensewhiz.presentation.setting_screen.service.LimitResetWorker
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@DelicateCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun LimitContent(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val MILLISECS = 86_400_000L
    val limitDuration = listOf(1 * MILLISECS, 7 * MILLISECS, 30 * MILLISECS)
    val limitDurationText by remember {
        mutableStateOf(
            listOf("Daily", "Weekly", "Monthly")
        )
    }
    var selectedIndex by remember { mutableStateOf(0) }
    val expenseLimitAmount by settingViewModel.expenseLimit.collectAsState()
    val expenseLimitDuration by settingViewModel.expenseLimitDuration.collectAsState()
    var selectedLimit by remember { mutableStateOf(limitDurationText[expenseLimitDuration]) }
    var isAmountEmpty by remember { mutableStateOf(false) }
    var limitTextFieldValue by remember { mutableStateOf(TextFieldValue(String())) }
    var expandedState by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    val context = LocalContext.current
    var resetWorkRequest = PeriodicWorkRequestBuilder<LimitResetWorker>(
        limitDuration.first(),
        TimeUnit.MILLISECONDS,
        (limitDuration.first() * 0.95).toLong(),
        TimeUnit.MILLISECONDS
    ).build()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = "SET LIMIT",
            style = MaterialTheme.typography.subtitle2
        )

        TextField(
            value = limitTextFieldValue,
            onValueChange = { field ->
                isAmountEmpty = false
                limitTextFieldValue = field
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.medium
                ),
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text(
                    text = if (expenseLimitAmount == 0.0) "Amount" else expenseLimitAmount.toString(),
                    style = MaterialTheme.typography.subtitle2
                )
            },
            isError = isAmountEmpty,
            textStyle = MaterialTheme.typography.subtitle2,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.primary,
                backgroundColor = Color.LightGray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandedState = !expandedState
                }
                .background(Color.LightGray)
                .padding(MaterialTheme.spacing.medium)
                .onGloballyPositioned {
                    size = it.size.toSize()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedLimit,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.pop_up),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )

            DropdownMenu(
                expanded = expandedState,
                onDismissRequest = { expandedState = false },
                modifier = Modifier.width(
                    with(LocalDensity.current) {
                        size.width.toDp()
                    }
                )
            ) {
                limitDurationText.forEachIndexed { index, label ->
                    DropdownMenuItem(onClick = {
                        selectedLimit = label
                        expandedState = false
                        selectedIndex = index
                        resetWorkRequest = PeriodicWorkRequestBuilder<LimitResetWorker>(
                            limitDuration[index],
                            TimeUnit.MILLISECONDS,
                            (limitDuration[index] * 0.95).toLong(),
                            TimeUnit.MILLISECONDS
                        ).build()
                    }) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            onClick = {
                scope.launch { modalBottomSheetState.hide() }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray.copy(alpha = 0.4f),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "CANCEL",
                style = MaterialTheme.typography.button
            )
        }

        TextButton(
            onClick = {
                scope.launch {
                    val amount = limitTextFieldValue.text
                    if (amount.isBlank())
                        isAmountEmpty = true
                    else {
                        isAmountEmpty = false
                        settingViewModel.editExpenseLimit(limitTextFieldValue.text.toDouble())
                        modalBottomSheetState.hide()
                        settingViewModel.editLimitDuration(selectedIndex)
                        val workManager = WorkManager.getInstance(context)
                        workManager.enqueueUniquePeriodicWork(
                            "RESET",
                            ExistingPeriodicWorkPolicy.REPLACE,
                            resetWorkRequest
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "SET",
                style = MaterialTheme.typography.button
            )
        }
    }
}