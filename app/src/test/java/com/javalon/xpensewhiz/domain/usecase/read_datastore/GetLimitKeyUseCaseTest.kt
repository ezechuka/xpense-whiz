package com.javalon.xpensewhiz.domain.usecase.read_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetLimitKeyUseCaseTest {
    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var getLimitKeyUseCase: GetLimitKeyUseCase

    @Before
    fun setup() {
        datastoreRepository = mock()
        getLimitKeyUseCase = GetLimitKeyUseCase(datastoreRepository)
    }

    @Test
    fun `retrieve limit enabled key`(): Unit = runBlocking {
        getLimitKeyUseCase()
        verify(datastoreRepository, times(1)).readLimitKeyFromDataStore()
    }
}