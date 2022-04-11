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

    override fun getDailyTransaction(entryDate: String) : Flow<List<TransactionDto>> {
        return dao.getDailyTransaction(entryDate)
    }

    override fun getAccount(account: String): Flow<AccountDto> {
        return dao.getAccount(account)
    }

    override fun getAccounts(): Flow<List<AccountDto>> {
        return dao.getAccounts()
    }

    override fun getMonthlyTransaction(): Flow<List<TransactionDto>> {
       return dao.getMonthlyTransaction()
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

    override fun get3DayTransaction(transaction_type: String): Flow<List<TransactionDto>> {
        return dao.get3DayTransaction(transaction_type)
    }

    override fun get7DayTransaction(transaction_type: String): Flow<List<TransactionDto>> {
        return dao.get7DayTransaction(transaction_type)
    }

    override fun get14DayTransaction(transaction_type: String): Flow<List<TransactionDto>> {
        return dao.get14DayTransaction(transaction_type)
    }

    override fun getStartOfMonthTransaction(transaction_type: String): Flow<List<TransactionDto>> {
        return dao.getStartOfMonthTransaction(transaction_type)
    }

    override fun getLastMonthTransaction(transaction_type: String): Flow<List<TransactionDto>> {
        return dao.getLastMonthTransaction(transaction_type)
    }

    override fun allTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.allTransaction(transactionType)
    }
}