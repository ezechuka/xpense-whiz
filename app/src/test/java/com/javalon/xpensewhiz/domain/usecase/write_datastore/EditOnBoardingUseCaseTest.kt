package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class EditOnBoardingUseCaseTest {
    private lateinit var datastoreRepository: DatastoreRepository
    private lateinit var editOnBoardingUseCase: EditOnBoardingUseCase

    @Before
    fun setUp() {
        datastoreRepository = mock()
        editOnBoardingUseCase = EditOnBoardingUseCase(datastoreRepository)
    }

    @Test
    fun `modifies onboarding completed key`() = runBlocking {
        editOnBoardingUseCase(anyBoolean())
        verify(datastoreRepository, times(1)).writeOnboardingKeyToDataStore(anyBoolean())
    }
}