package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import com.javalon.xpensewhiz.domain.usecase.write_database.MockitoHelper.anyObject
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class InsertNewTransactionUseCaseTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var insertNewTransactionUseCase: InsertNewTransactionUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        insertNewTransactionUseCase = InsertNewTransactionUseCase(transactionRepository)
    }

    @Test
    fun `insert a new transaction correctly adds it to the database`() = runBlocking {
        insertNewTransactionUseCase(anyObject())
        verify(transactionRepository, times(1)).insertTransaction(anyObject())
    }
}

object MockitoHelper {
    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}
