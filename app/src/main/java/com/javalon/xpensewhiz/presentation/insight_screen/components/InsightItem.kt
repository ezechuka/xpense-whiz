package com.javalon.xpensewhiz.presentation.insight_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javalon.xpensewhiz.presentation.home_screen.Category
import com.javalon.xpensewhiz.presentation.home_screen.amountFormat
import com.javalon.xpensewhiz.util.spacing

@Composable
fun InsightItem(cat: Category, currencyCode: String, amount: Double, percent: Float) {
    Card(
        elevation = 0.dp,
        backgroundColor = Color.DarkGray.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(vertical = MaterialTheme.spacing.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        ) {
            Icon(
                painter = painterResource(id = cat.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .background(cat.bgRes, shape = CircleShape)
                    .padding(16.dp),
                tint = cat.colorRes
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                Text(
                    text = cat.title,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = currencyCode + "$amount".amountFormat(),
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W600),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start
                )
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "${String.format("%.2f", percent)}%",
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End
                )
            }

        }
    }
}