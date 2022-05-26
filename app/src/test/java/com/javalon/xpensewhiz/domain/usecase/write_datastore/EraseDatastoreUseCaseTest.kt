package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class EraseDatastoreUseCaseTest {
    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var eraseDatastoreUseCase: EraseDatastoreUseCase

    @Before
    fun setup() {
        datastoreRepository = mock()
        eraseDatastoreUseCase = EraseDatastoreUseCase(datastoreRepository)
    }

    @Test
    fun `when erase datastore usecase is invoked should call eraseDataStore() from repository`() = runBlocking {
        eraseDatastoreUseCase.invoke()
        verify(datastoreRepository, times(1)).eraseDataStore()
    }
}