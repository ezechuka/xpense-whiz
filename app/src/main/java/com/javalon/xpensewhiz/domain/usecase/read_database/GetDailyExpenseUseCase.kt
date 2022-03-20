package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import com.javalon.xpensewhiz.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyExpenseUseCase @Inject constructor(private val repo: ExpenseRepository) {

    operator fun invoke(entryDate: String): Flow<ExpenseDto?> {
        return repo.getDailyExpense(entryDate)
    }
}