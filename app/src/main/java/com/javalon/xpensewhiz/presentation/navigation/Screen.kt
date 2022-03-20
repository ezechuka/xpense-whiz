package com.javalon.xpensewhiz.presentation.navigation

sealed class Screen(val route: String) {
    object WelcomeScreen: Screen("welcome")
    object CurrencyScreen: Screen("currency")
    object HomeScreen: Screen("home")
}