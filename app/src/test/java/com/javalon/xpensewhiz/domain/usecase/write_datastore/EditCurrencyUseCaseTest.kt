package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class EditCurrencyUseCaseTest {

    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var editCurrencyUseCase: EditCurrencyUseCase

    @Before
    fun setUp() {
        datastoreRepository = mock()
        editCurrencyUseCase = EditCurrencyUseCase(datastoreRepository)
    }

    @Test
    fun `modifies currency`() = runBlocking {
        editCurrencyUseCase(anyString())
        verify(datastoreRepository, times(1)).writeCurrencyToDataStore(anyString())
    }

}