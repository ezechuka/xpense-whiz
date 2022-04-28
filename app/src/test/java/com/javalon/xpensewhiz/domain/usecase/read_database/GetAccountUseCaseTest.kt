package com.javalon.xpensewhiz.domain.usecase.read_database

import com.javalon.xpensewhiz.domain.repository.TransactionRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetAccountUseCaseTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getAccountUseCase: GetAccountUseCase

    @Before
    fun setUp() {
        transactionRepository = mock()
        getAccountUseCase = GetAccountUseCase(transactionRepository)
    }

    @Test
    fun `retrieve a specified account from the account records in database`() : Unit = runBlocking {
        getAccountUseCase(anyString())
        verify(transactionRepository, times(1)).getAccount(anyString())
    }
}