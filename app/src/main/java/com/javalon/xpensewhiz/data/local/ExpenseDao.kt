package com.javalon.xpensewhiz.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javalon.xpensewhiz.data.local.entity.ExpenseDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseDto)

    @Query("SELECT * FROM expense_table WHERE entry_date = :entryDate")
    fun getDailyExpense(entryDate: String) : Flow<List<ExpenseDto>>

    @Query("SELECT * FROM expense_table")
    fun getMonthlyExpense() : Flow<List<ExpenseDto>>
}
