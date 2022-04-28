package com.javalon.xpensewhiz.domain.usecase.write_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class InsertAccountsUseCaseTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var insertAccountsUseCase: InsertAccountsUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        insertAccountsUseCase = InsertAccountsUseCase(transactionRepository)
    }

    @Test
    fun `correctly inserts list of accounts to database`() = runBlocking {
        insertAccountsUseCase(anyList())
        verify(transactionRepository, times(1)).insertAccount(anyList())
    }
}