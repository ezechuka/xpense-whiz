package com.javalon.xpensewhiz.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DatastoreRepository {
    suspend fun writeOnboardingKeyToDataStore(completed: Boolean)

    suspend fun readOnboardingKeyFromDataStore(): Flow<Preferences>

    suspend fun writeCurrencyToDataStore(currency: String)

    suspend fun readCurrencyFromDataStore(): Flow<Preferences>

    suspend fun writeExpenseLimitToDataStore(amount: Double)

    suspend fun readExpenseLimitFromDataStore(): Flow<Preferences>

    suspend fun writeLimitKeyToDataStore(enabled: Boolean)

    suspend fun readLimitKeyFromDataStore(): Flow<Preferences>
}