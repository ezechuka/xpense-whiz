package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javalon.xpensewhiz.presentation.home_screen.TransactionType
import com.javalon.xpensewhiz.presentation.ui.theme.Amber500
import com.javalon.xpensewhiz.util.spacing

@Composable
fun InfoBanner(shown: Boolean, transactionType: TransactionType) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // Enters by sliding in from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding out from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.medium),
            color = Amber500,
            shape = RoundedCornerShape(16.dp),
            elevation = 1.dp
        ) {
            Text(
                text = "Invalid ${transactionType.title} amount",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}