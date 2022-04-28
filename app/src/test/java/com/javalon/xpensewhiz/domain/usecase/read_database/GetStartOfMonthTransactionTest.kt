package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetStartOfMonthTransactionTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getStartOfMonthTransaction: GetStartOfMonthTransaction

    @Before
    fun setUp() {
        transactionRepository = mock()
        getStartOfMonthTransaction = GetStartOfMonthTransaction(transactionRepository)
    }

    @Test
    fun `retrieve transactions since start of month correctly`(): Unit = runBlocking {
        getStartOfMonthTransaction(anyString())
        verify(transactionRepository, times(1)).getStartOfMonthTransaction(anyString())
    }
}