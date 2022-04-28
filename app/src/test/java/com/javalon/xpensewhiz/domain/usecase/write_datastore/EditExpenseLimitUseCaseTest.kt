package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class EditExpenseLimitUseCaseTest {

    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var editExpenseLimitUseCase: EditExpenseLimitUseCase

    @Before
    fun setUp() {
        datastoreRepository = mock()
        editExpenseLimitUseCase = EditExpenseLimitUseCase(datastoreRepository)
    }

    @Test
    fun `modifies the expense limit amount`() = runBlocking {
        editExpenseLimitUseCase(anyDouble())
        verify(datastoreRepository, times(1)).writeExpenseLimitToDataStore(anyDouble())
    }
}