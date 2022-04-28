package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class EraseTransactionUseCaseTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var eraseTransactionUseCase: EraseTransactionUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        eraseTransactionUseCase = EraseTransactionUseCase(transactionRepository)
    }

    @Test
    fun `erase transaction, clears all transaction records from database`() {
        eraseTransactionUseCase.invoke()
        verify(transactionRepository, times(1)).eraseTransaction()
    }
}