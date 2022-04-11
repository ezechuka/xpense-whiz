package com.javalon.xpensewhiz.domain.repository

import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(dailyExpense: TransactionDto)

    suspend fun insertAccount(accounts: List<AccountDto>)

    fun getDailyTransaction(entryDate: String) : Flow<List<TransactionDto>>

    fun getAccount(account: String): Flow<AccountDto>

    fun getAccounts(): Flow<List<AccountDto>>

    fun getMonthlyTransaction() : Flow<List<TransactionDto>>

    fun eraseTransaction()

    fun getCurrentDayExpTransaction(): Flow<List<TransactionDto>>

    fun getWeeklyExpTransaction(): Flow<List<TransactionDto>>

    fun getMonthlyExpTransaction(): Flow<List<TransactionDto>>

    fun get3DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    fun get7DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    fun get14DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    fun getStartOfMonthTransaction(transaction_type: String): Flow<List<TransactionDto>>

    fun getLastMonthTransaction(transaction_type: String): Flow<List<TransactionDto>>

    fun allTransaction(transactionType: String): Flow<List<TransactionDto>>
}