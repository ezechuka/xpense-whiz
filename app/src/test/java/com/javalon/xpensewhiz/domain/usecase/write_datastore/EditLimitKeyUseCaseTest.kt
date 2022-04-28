package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class EditLimitKeyUseCaseTest {

    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var editLimitKeyUseCase: EditLimitKeyUseCase

    @Before
    fun setUp() {
        datastoreRepository = mock()
        editLimitKeyUseCase = EditLimitKeyUseCase(datastoreRepository)
    }

    @Test
    fun `modify limit enabled key`() = runBlocking {
        editLimitKeyUseCase(anyBoolean())
        verify(datastoreRepository, times(1)).writeLimitKeyToDataStore(anyBoolean())
    }
}