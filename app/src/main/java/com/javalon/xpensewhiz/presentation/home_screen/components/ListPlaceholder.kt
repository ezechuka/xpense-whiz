package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.util.spacing

@Composable
fun ListPlaceholder(
    label: String =
        "No transaction has been made so far. Tap the '+' button to  get started."
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(
            start = MaterialTheme.spacing.medium,
            top = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium
        ).fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.blank_list),
            contentDescription = "no item added."
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle2,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}