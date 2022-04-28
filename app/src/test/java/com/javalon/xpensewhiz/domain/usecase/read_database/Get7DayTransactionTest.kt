package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class Get7DayTransactionTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var get7DayTransaction: Get7DayTransaction

    @Before
    fun setUp() {
        transactionRepository = mock()
        get7DayTransaction = Get7DayTransaction(transactionRepository)
    }

    @Test
    fun `retrieves all records of transaction occurred in the past 7 days`(): Unit = runBlocking {
        get7DayTransaction(ArgumentMatchers.anyString())
        verify(transactionRepository, times(1)).get7DayTransaction(ArgumentMatchers.anyString())
    }
}