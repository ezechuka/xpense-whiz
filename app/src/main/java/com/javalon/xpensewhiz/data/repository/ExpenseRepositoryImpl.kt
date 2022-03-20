package com.javalon.xpensewhiz.data.repository

import com.javalon.xpensewhiz.data.local.ExpenseDao
import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import com.javalon.xpensewhiz.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(private val dao: ExpenseDao) : ExpenseRepository {
    override suspend fun insertExpense(dailyExpense: ExpenseDto) {
        dao.insertExpense(expense = dailyExpense)
    }

    override fun getDailyExpense(entryDate: String) : Flow<List<ExpenseDto>> {
        return dao.getDailyExpense(entryDate)
    }

    override fun getMonthlyExpense(): Flow<List<ExpenseDto>> {
       return dao.getMonthlyExpense()
    }
}