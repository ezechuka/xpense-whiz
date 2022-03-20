package com.javalon.xpensewhiz.presentation.welcome_screen.components

import androidx.annotation.DrawableRes
import com.javalon.xpensewhiz.R

sealed class OnBoardingPage(
    @DrawableRes
    val icon: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnBoardingPage(
        R.drawable.entry, "Add entries", "Keep track of your income and expenses"
    )

    object SecondPage : OnBoardingPage(
        R.drawable.insight,
        "Check insights",
        "Detailed weekly and monthly charts based on your entries"
    )

    object ThirdPage : OnBoardingPage(
        R.drawable.decision,
        "Make right decisions",
        "Control your money flow and stay on top of your game"
    )
}