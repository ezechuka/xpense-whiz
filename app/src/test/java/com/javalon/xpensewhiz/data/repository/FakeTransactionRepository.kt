package com.javalon.xpensewhiz.data.repository

import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import com.javalon.xpensewhiz.presentation.home_screen.Account
import com.javalon.xpensewhiz.presentation.home_screen.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date

class FakeTransactionRepository : TransactionRepository {
    private val date: Date = Calendar.getInstance().time
    private val trxList = listOf(
        TransactionDto(
            date,
            "2022-04-28",
            500.0,
            Account.CASH.title,
            Category.FOOD_DRINK.title,
            "expense",
            ""
        ),
        TransactionDto(
            date,
            "2022-04-28",
            200.0,
            Account.BANK.title,
            Category.MISC.title,
            "expense",
            ""
        )
    )
    private val accList = listOf(
        AccountDto(1, Account.CASH.title, 5.0, 10.0, 5.0),
        AccountDto(2, Account.CARD.title, 5.0, 10.0, 5.0),
        AccountDto(3, Account.BANK.title, 5.0, 10.0, 5.0)
    )

    override suspend fun insertTransaction(dailyExpense: TransactionDto) {
        return
    }

    override suspend fun insertAccount(accounts: List<AccountDto>) {
        return
    }

    override fun getDailyTransaction(entryDate: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getTransactionByAccount(accountType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getAccount(account: String): Flow<AccountDto> {
        return flow {
            emit(accList[0])
        }
    }

    override fun getAccounts(): Flow<List<AccountDto>> {
        return flow {
            emit(accList)
        }
    }

    override fun getAllTransaction(): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun eraseTransaction() {

    }

    override fun getCurrentDayExpTransaction(): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getWeeklyExpTransaction(): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getMonthlyExpTransaction(): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun get7DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }

    override fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>> {
        return flow {
            emit(trxList)
        }
    }
}