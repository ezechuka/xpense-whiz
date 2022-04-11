package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import javax.inject.Inject

class EraseTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke() {
        repository.eraseTransaction()
    }
}