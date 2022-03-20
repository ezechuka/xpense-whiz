package com.javalon.xpensewhiz.domain.model

import java.util.Date

data class Expense(
    val date: Date,
    val amount: Double,
    val expenseType: String
)