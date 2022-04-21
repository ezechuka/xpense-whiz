package com.javalon.xpensewhiz.presentation.setting_screen.components

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.setting_screen.SettingViewModel
import com.javalon.xpensewhiz.presentation.setting_screen.service.LimitResetJobService
import com.javalon.xpensewhiz.util.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun LimitContent(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val MILLISECS = 86_400_000L
    val limitDuration = listOf(1*MILLISECS, 7*MILLISECS, 30*MILLISECS)
    val limitDurationText by remember {
        mutableStateOf(
            listOf("Daily", "Weekly", "Monthly")
        )
    }
    var selectedLimit by remember { mutableStateOf(limitDurationText.first()) }
    var isAmountEmpty by remember { mutableStateOf(false) }
    var limitTextFieldValue by remember { mutableStateOf(TextFieldValue(String())) }
    var expandedState by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    val context = LocalContext.current
    val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    var resetJob: JobInfo = JobInfo.Builder(500, ComponentName(context, LimitResetJobService::class.java))
        .setPeriodic(limitDuration.first())
        .build()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
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
                    text = "Amount",
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
                        resetJob = JobInfo.Builder(500, ComponentName(context, LimitResetJobService::class.java))
                            .setPeriodic(limitDuration[index])
                            .build()
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
                        jobScheduler.schedule(resetJob)
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

        TextButton(
            onClick = {
                scope.launch { modalBottomSheetState.hide() }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "CANCEL",
                style = MaterialTheme.typography.button
            )
        }
    }
}