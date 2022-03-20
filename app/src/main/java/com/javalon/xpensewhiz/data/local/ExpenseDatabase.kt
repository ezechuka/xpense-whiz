package com.javalon.xpensewhiz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.javalon.xpensewhiz.data.local.converter.DateConverter
import com.javalon.xpensewhiz.data.local.entity.ExpenseDto

@TypeConverters(value = [DateConverter::class])
@Database(entities = [ExpenseDto::class], exportSchema = true, version = 1)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract val expenseDao: ExpenseDao
}