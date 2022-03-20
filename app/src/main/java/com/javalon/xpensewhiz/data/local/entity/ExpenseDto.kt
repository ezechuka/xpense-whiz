package com.javalon.xpensewhiz.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javalon.xpensewhiz.domain.model.Expense
import java.util.*

@Entity(tableName = "expense_table")
data class ExpenseDto(
    @PrimaryKey
    @ColumnInfo(name = "entry_date")
    val dateOfEntry: String,
    @ColumnInfo(name = "timestamp")
    val date: Date,
    @ColumnInfo(name = "expenses")
    val expenseList: List<ExpenseInfo>
) {
    fun toExpense() = Expense(date, expenseList)
}

data class ExpenseInfo(
    val amount: Double,
    val date: Date,
    val expenseType: String
)