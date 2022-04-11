package com.javalon.xpensewhiz.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.javalon.xpensewhiz.presentation.navigation.components.BottomNavBar
import com.javalon.xpensewhiz.presentation.navigation.components.provideBottomNavItems

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalUnitApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    startDestination: String
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val rootDestinations = listOf(
        Screen.HomeScreen.route,
        Screen.InsightScreen.route,
        Screen.AccountScreen.route,
        Screen.SettingScreen.route
    )

    val bottomNavBarState = remember { mutableStateOf(true) }

    val navBarEntry by navController.currentBackStackEntryAsState()
    bottomNavBarState.value = rootDestinations.contains(navBarEntry?.destination?.route)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomNavBarState.value,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavBar(
                    items = provideBottomNavItems(), navController
                ) {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }, scaffoldState = scaffoldState
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(
                bottom = it.calculateBottomPadding()
            )
        ) {
            MainNavigation(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}