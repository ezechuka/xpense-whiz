package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.presentation.home_screen.TabButton
import com.javalon.xpensewhiz.util.spacing

@Composable
fun TabButton(
    tabs: Array<TabButton> = TabButton.values(),
    cornerRadius: Dp = 24.dp,
    onButtonClick: () -> Unit = { },
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val selectedTab by homeViewModel.tabButton.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.small
            )
            .background(
                color = Color.DarkGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        tabs.forEachIndexed { index, tab ->
            val backgroundColor by animateColorAsState(
                if (selectedTab == tab) MaterialTheme.colors.onSurface
                else Color.Transparent,
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            )

            val textColor by animateColorAsState(
                if (selectedTab == tab) Color.White
                else MaterialTheme.colors.onSurface,
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            )

            OutlinedButton(
                onClick = {
                    homeViewModel.selectTabButton(tab)
                    onButtonClick()
                },
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.extraSmall)
                    .weight(1f),
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = cornerRadius,
                        bottomStart = cornerRadius,
                        bottomEnd = cornerRadius
                    )
                    tabs.size - 1 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = cornerRadius,
                        bottomStart = cornerRadius,
                        bottomEnd = cornerRadius
                    )
                    else -> RoundedCornerShape(0.dp)
                },
                border = BorderStroke(
                    0.dp, Color.Transparent
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = backgroundColor,
                    contentColor = textColor
                )
            ) {
                Text(
                    text = tab.title,
                    color = if (selectedTab == tab) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.onSurface
                    },
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.spacing.small,
                            vertical = MaterialTheme.spacing.extraSmall
                        )
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}