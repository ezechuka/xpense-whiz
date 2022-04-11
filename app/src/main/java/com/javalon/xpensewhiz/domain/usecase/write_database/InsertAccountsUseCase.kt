package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertAccountsUseCase @Inject constructor(private val repo: TransactionRepository) {

    suspend operator fun invoke(account: List<AccountDto>) {
        repo.insertAccount(account)
    }
}