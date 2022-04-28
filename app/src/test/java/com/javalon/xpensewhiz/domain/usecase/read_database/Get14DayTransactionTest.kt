package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class Get14DayTransactionTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var get14DayTransaction: Get14DayTransaction

    @Before
    fun setUp() {
        transactionRepository = mock()
        get14DayTransaction = Get14DayTransaction(transactionRepository)
    }

    @Test
    fun `retrieves all records of transaction occurred in the past 14 days`(): Unit = runBlocking {
        get14DayTransaction(anyString())
        verify(transactionRepository, times(1)).get14DayTransaction(anyString())
    }
}