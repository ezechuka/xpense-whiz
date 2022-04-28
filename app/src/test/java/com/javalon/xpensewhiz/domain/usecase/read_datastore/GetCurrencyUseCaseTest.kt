package com.javalon.xpensewhiz.domain.usecase.read_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetCurrencyUseCaseTest {

    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var getCurrencyUseCase: GetCurrencyUseCase

    @Before
    fun setUp() {
        datastoreRepository = mock()
        getCurrencyUseCase = GetCurrencyUseCase(datastoreRepository)
    }

    @Test
    fun `retrieve selected currency`(): Unit = runBlocking {
        getCurrencyUseCase()
        verify(datastoreRepository, times(1)).readCurrencyFromDataStore()
    }
}