package com.javalon.xpensewhiz.domain.usecase.write_datastore

import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import javax.inject.Inject

class EditLimitDurationUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(duration: Int) {
        return datastoreRepository.writeLimitDurationToDataStore(duration)
    }
}