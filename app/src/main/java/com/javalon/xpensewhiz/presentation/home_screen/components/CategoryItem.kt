package com.javalon.xpensewhiz.presentation.home_screen.components
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.javalon.xpensewhiz.presentation.home_screen.Category
//import com.javalon.xpensewhiz.presentation.home_screen.HomeViewModel
//import com.javalon.xpensewhiz.presentation.ui.theme.LightBlue3
//
//@Composable
//fun CategoryItem(item: Category, homeViewModel: HomeViewModel = hiltViewModel()) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//        val isSelected = homeViewModel.category.value == item
//        IconButton(
//            onClick = {
//                homeViewModel.selectCategory(item)
//            }
//        ) {
//            Icon(
//                painter = painterResource(id = item.iconRes),
//                contentDescription = item.title,
//                tint = if (isSelected) LightBlue3
//                else MaterialTheme.colors.onSurface
//            )
//        }
//
//        Text(
//            text = item.title,
//            style = MaterialTheme.typography.subtitle2,
//            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            color = if (isSelected) LightBlue3
//            else MaterialTheme.colors.onSurface
//        )
//    }
//}