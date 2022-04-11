package com.javalon.xpensewhiz.domain.model

data class Account(
    val account: String, val amount: Double,
    val income: Double, val expense: Double
)