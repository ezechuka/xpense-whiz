package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetCurrentDayExpTransactionUseCaseTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getCurrentDayExpTransactionUseCase: GetCurrentDayExpTransactionUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        getCurrentDayExpTransactionUseCase = GetCurrentDayExpTransactionUseCase(transactionRepository)
    }

    @Test
    fun `retrieve current day expenses transactions correctly`(): Unit = runBlocking {
        getCurrentDayExpTransactionUseCase()
        verify(transactionRepository, times(1)).getCurrentDayExpTransaction()
    }
}