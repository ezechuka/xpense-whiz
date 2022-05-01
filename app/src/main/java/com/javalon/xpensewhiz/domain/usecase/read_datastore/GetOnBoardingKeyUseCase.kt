package com.javalon.xpensewhiz.domain.usecase.read_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardingKeyUseCase @Inject constructor(private val datastoreRepository: DatastoreRepository) {
    suspend operator fun invoke() : Flow<Boolean> {
        return datastoreRepository.readOnboardingKeyFromDataStore()
    }
}