package com.javalon.xpensewhiz.domain.usecase.read_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetExpenseLimitUseCaseTest {

    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var getExpenseLimitUseCase: GetExpenseLimitUseCase

    @Before
    fun setup() {
        datastoreRepository = mock()
        getExpenseLimitUseCase = GetExpenseLimitUseCase(datastoreRepository)
    }

    @Test
    fun `retrieve expense limit amount`(): Unit = runBlocking {
        getExpenseLimitUseCase()
        verify(datastoreRepository, times(1)).readExpenseLimitFromDataStore()
    }
}