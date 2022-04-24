package com.javalon.xpensewhiz.data.repository

import com.javalon.xpensewhiz.data.local.TransactionDao
import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val dao: TransactionDao) : TransactionRepository {
    override suspend fun insertTransaction(dailyExpense: TransactionDto) {
        dao.insertTransaction(transaction = dailyExpense)
    }

    override suspend fun insertAccount(accounts: List<AccountDto>) {
        dao.insertAccounts(accounts)
    }

    override fun getTransactionByAccount(accountType: String): Flow<List<TransactionDto>> {
        return dao.getTransactionByAccount(accountType)
    }

    override fun getDailyTransaction(entryDate: String) : Flow<List<TransactionDto>> {
        return dao.getDailyTransaction(entryDate)
    }

    override fun getAccount(account: String): Flow<AccountDto> {
        return dao.getAccount(account)
    }

    override fun getAccounts(): Flow<List<AccountDto>> {
        return dao.getAccounts()
    }

    override fun getAllTransaction(): Flow<List<TransactionDto>> {
       return dao.getAllTransaction()
    }

    override fun eraseTransaction() {
        dao.eraseTransaction()
    }

    override fun getCurrentDayExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getCurrentDayExpTransaction()
    }

    override fun getWeeklyExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getWeeklyExpTransaction()
    }

    override fun getMonthlyExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getMonthlyExpTransaction()
    }

    override fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get3DayTransaction(transactionType)
    }

    override fun get7DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get7DayTransaction(transactionType)
    }

    override fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get14DayTransaction(transactionType)
    }

    override fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getStartOfMonthTransaction(transactionType)
    }

    override fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getLastMonthTransaction(transactionType)
    }

    override fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getTransactionByType(transactionType)
    }
}