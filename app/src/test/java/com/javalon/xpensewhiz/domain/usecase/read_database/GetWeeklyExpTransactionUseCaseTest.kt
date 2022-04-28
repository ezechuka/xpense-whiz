package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetWeeklyExpTransactionUseCaseTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getWeeklyExpTransactionUseCase: GetWeeklyExpTransactionUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        getWeeklyExpTransactionUseCase = GetWeeklyExpTransactionUseCase(transactionRepository)
    }

    @Test
    fun `retrieve transactions according to transaction type`(): Unit = runBlocking {
        getWeeklyExpTransactionUseCase()
        verify(transactionRepository, times(1)).getWeeklyExpTransaction()
    }
}