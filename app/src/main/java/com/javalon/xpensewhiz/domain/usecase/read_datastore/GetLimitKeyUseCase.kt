package com.javalon.xpensewhiz.domain.usecase.read_datastore

import androidx.datastore.preferences.core.Preferences
import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLimitKeyUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(): Flow<Preferences> {
        return datastoreRepository.readLimitKeyFromDataStore()
    }
}