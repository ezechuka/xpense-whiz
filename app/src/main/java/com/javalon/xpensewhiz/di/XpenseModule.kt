package com.javalon.xpensewhiz.di

import android.content.Context
import androidx.room.Room
import com.javalon.xpensewhiz.data.local.ExpenseDao
import com.javalon.xpensewhiz.data.local.ExpenseDatabase
import com.javalon.xpensewhiz.data.repository.DatastoreRepositoryImpl
import com.javalon.xpensewhiz.data.repository.ExpenseRepositoryImpl
import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import com.javalon.xpensewhiz.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object XpenseModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(@ApplicationContext context: Context) : DatastoreRepository {
        return DatastoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao) : ExpenseRepository
        = ExpenseRepositoryImpl(expenseDao)

    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context) : ExpenseDatabase {
        return Room.databaseBuilder(context, ExpenseDatabase::class.java, "expenseDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(database: ExpenseDatabase) = database.expenseDao

}