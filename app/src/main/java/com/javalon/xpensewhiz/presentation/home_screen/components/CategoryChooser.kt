package com.javalon.xpensewhiz.presentation.home_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.home_screen.Category
import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
import com.javalon.xpensewhiz.util.spacing

@ExperimentalUnitApi
@Composable
fun Category(
    expenseItems: Array<Category> = Category.values()
) {
    FlowRow(
        crossAxisSpacing = MaterialTheme.spacing.small,
        modifier = Modifier.padding(
            start = MaterialTheme.spacing.medium,
            top = MaterialTheme.spacing.medium,
            bottom = MaterialTheme.spacing.medium,
        ),
    ) {
        expenseItems.forEach {
            CategoryTag(category = it)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoryTag(category: Category, homeViewModel: HomeViewModel = hiltViewModel()) {
    val selected by homeViewModel.category.collectAsState()
    var isSelected = selected.title == category.title
    TextButton(
        modifier = Modifier.padding(end = MaterialTheme.spacing.small),
        onClick = {
            homeViewModel.selectCategory(category)
            isSelected = selected.title == category.title
        },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) {
                category.bgRes.copy(alpha = 0.95f)
            } else MaterialTheme.colors.surface, contentColor = if (isSelected) {
                category.colorRes
            } else MaterialTheme.colors.onSurface
        ),
    ) {
        Icon(
            painter = if (!isSelected) {
                painterResource(id = R.drawable.add_cat)
            } else painterResource(id = category.iconRes),
            contentDescription = category.title,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = category.title,
            style = MaterialTheme.typography.button
        )
    }
}

@ExperimentalUnitApi
@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    Category()
}