package com.javalon.xpensewhiz.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javalon.xpensewhiz.domain.model.Expense
import java.util.Date

@Entity(tableName = "expense_table")
data class ExpenseDto(
    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    val date: Date,
    @ColumnInfo(name = "entry_date")
    val dateOfEntry: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "expense")
    val expenseType: String
) {
    fun toExpense() = Expense(date, amount, expenseType)
}