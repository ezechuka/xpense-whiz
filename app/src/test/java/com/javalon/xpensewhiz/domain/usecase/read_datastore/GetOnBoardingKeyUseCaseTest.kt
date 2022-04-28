package com.javalon.xpensewhiz.domain.usecase.read_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetOnBoardingKeyUseCaseTest {
    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var getOnBoardingUseCase: GetOnBoardingKeyUseCase

    @Before
    fun setup() {
        datastoreRepository = mock()
        getOnBoardingUseCase = GetOnBoardingKeyUseCase(datastoreRepository)
    }

    @Test
    fun `retrieve onboarding key`(): Unit = runBlocking {
        getOnBoardingUseCase()
        verify(datastoreRepository, times(1)).readOnboardingKeyFromDataStore()
    }
}