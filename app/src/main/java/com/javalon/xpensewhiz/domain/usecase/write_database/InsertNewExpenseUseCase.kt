package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import com.javalon.xpensewhiz.domain.repository.ExpenseRepository
import javax.inject.Inject

class InsertNewExpenseUseCase @Inject constructor(private val repo: ExpenseRepository) {

    suspend operator fun invoke(dailyExpense: ExpenseDto) {
        repo.insertExpense(dailyExpense)
    }
}