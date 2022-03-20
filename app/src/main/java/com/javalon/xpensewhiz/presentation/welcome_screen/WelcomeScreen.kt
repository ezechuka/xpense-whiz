package com.javalon.xpensewhiz.presentation.welcome_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.javalon.xpensewhiz.presentation.navigation.Screen
import com.javalon.xpensewhiz.presentation.welcome_screen.components.GetStartedButton
import com.javalon.xpensewhiz.presentation.welcome_screen.components.PagerScreen

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages by welcomeViewModel.listOfPages
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(10f)
        ) { pageCount ->
            PagerScreen(page = pages[pageCount])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            indicatorWidth = 12.dp,
            indicatorHeight = 4.dp
        )
        GetStartedButton(pagerState = pagerState, modifier = Modifier.weight(2f)) {
            navController.popBackStack()
            navController.navigate(Screen.CurrencyScreen.route)
        }
    }
}