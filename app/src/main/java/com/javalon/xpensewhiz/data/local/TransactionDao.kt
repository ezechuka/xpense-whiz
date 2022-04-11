package com.javalon.xpensewhiz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javalon.xpensewhiz.data.local.entity.AccountDto
import com.javalon.xpensewhiz.data.local.entity.TransactionDto
import com.javalon.xpensewhiz.presentation.home_screen.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountDto>)

    @Query("SELECT * FROM transaction_table WHERE entry_date = :entryDate")
    fun getDailyTransaction(entryDate: String) : Flow<List<TransactionDto>>

    @Query("SELECT * FROM account_table WHERE account = :account")
    fun getAccount(account: String) : Flow<AccountDto>

    @Query("SELECT * FROM account_table")
    fun getAccounts() : Flow<List<AccountDto>>

    @Query("SELECT * FROM transaction_table")
    fun getMonthlyTransaction() : Flow<List<TransactionDto>>

    @Query("DELETE FROM transaction_table")
    fun eraseTransaction()

    @Query("SELECT * FROM transaction_table WHERE entry_date = date('now', 'localtime') AND transaction_type = :transaction_type")
    fun getCurrentDayExpTransaction(transaction_type: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date BETWEEN date('now', '-7 day') AND date('now', 'localtime') AND transaction_type = :transaction_type")
    fun getWeeklyExpTransaction(transaction_type: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date BETWEEN date('now', '-1 month') AND date('now', 'localtime') AND transaction_type = :transaction_type")
    fun getMonthlyExpTransaction(transaction_type: String = TransactionType.EXPENSE.title): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date >= date('now', '-3 day') AND entry_date < date('now', 'localtime') AND transaction_type = :transaction_type")
    fun get3DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date >= date('now', '-7 day') AND entry_date < date('now', 'localtime') AND transaction_type = :transaction_type")
    fun get7DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date >= date('now', '-14 day') AND entry_date < date('now', 'localtime') AND transaction_type = :transaction_type")
    fun get14DayTransaction(transaction_type: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date >= date('now', 'start of month') AND entry_date < date('now', 'localtime') AND transaction_type = :transaction_type")
    fun getStartOfMonthTransaction(transaction_type: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE entry_date >= date('now', '-1 month') AND entry_date < date('now', 'localtime') AND transaction_type = :transaction_type")
    fun getLastMonthTransaction(transaction_type: String): Flow<List<TransactionDto>>

    @Query("SELECT * FROM transaction_table WHERE transaction_type = :transactionType")
    fun allTransaction(transactionType: String): Flow<List<TransactionDto>>
}
