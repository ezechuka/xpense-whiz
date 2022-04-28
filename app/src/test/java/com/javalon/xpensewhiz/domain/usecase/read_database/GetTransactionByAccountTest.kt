package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetTransactionByAccountTest {
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getTransactionByAccount: GetTransactionByAccount

    @Before
    fun setUp() {
        transactionRepository = mock()
        getTransactionByAccount = GetTransactionByAccount(transactionRepository)
    }

    @Test
    fun `retrieve transactions according to account type`(): Unit = runBlocking {
        getTransactionByAccount(anyString())
        verify(transactionRepository, times(1)).getTransactionByAccount(anyString())
    }
}