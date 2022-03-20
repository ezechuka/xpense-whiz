package com.javalon.xpensewhiz.domain.model

import com.javalon.xpensewhiz.data.local.entity.ExpenseInfo
import java.util.*

data class Expense(
    val date: Date,
    val expenseList: List<ExpenseInfo>
)