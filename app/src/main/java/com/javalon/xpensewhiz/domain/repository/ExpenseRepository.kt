package com.javalon.xpensewhiz.domain.repository

import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import com.javalon.xpensewhiz.util.Resource
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    suspend fun insertExpense(dailyExpense: ExpenseDto)

    fun getDailyExpense(entryDate: String) : Flow<ExpenseDto>

    fun getMonthlyExpense() : Flow<List<ExpenseDto>>
}