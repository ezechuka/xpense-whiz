package com.javalon.xpensewhiz.presentation.navigation

sealed class Screen(val route: String) {
    object WelcomeScreen: Screen("welcome")
    object CurrencyScreen: Screen("currency")
    object HomeScreen: Screen("home")
    object TransactionScreen: Screen("transaction")
    object InsightScreen: Screen("insight")
    object AccountScreen: Screen("account")
    object AccountDetailScreen: Screen("detail")
    object SettingScreen: Screen("setting")
}