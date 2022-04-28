package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetDailyTransactionUseCaseTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getDailyTransactionUseCase: GetDailyTransactionUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        getDailyTransactionUseCase = GetDailyTransactionUseCase(transactionRepository)
    }

    @Test
    fun `retrieve daily transactions correctly`(): Unit = runBlocking {
        getDailyTransactionUseCase(anyString())
        verify(transactionRepository, times(1)).getDailyTransaction(anyString())
    }
}