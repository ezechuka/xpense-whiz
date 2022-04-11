package com.javalon.xpensewhiz.presentation.navigation.components

import com.javalon.xpensewhiz.R
import com.javalon.xpensewhiz.presentation.navigation.Screen

data class NavBarItemHolder(
    val title: String,
    val icon: Int,
    val route: String
)

fun provideBottomNavItems() = listOf(
    NavBarItemHolder(
        "",
        R.drawable.main_home,
        Screen.HomeScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.donut,
        Screen.InsightScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.account,
        Screen.AccountScreen.route
    ),
    NavBarItemHolder(
        "",
        R.drawable.setting,
        Screen.SettingScreen.route
    )
)