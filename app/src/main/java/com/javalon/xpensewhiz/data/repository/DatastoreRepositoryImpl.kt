package com.javalon.xpensewhiz.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "whiz_key_store")
class DatastoreRepositoryImpl @Inject constructor(context: Context): DatastoreRepository {

    private val datastore = context.datastore
    private val onBoardingKey = booleanPreferencesKey(Constants.WELCOME_KEY)
    private val limitKey = booleanPreferencesKey(Constants.LIMIT_KEY)
    private val selectedCurrency = stringPreferencesKey(Constants.CURRENCY_KEY)
    private val expenseLimit = doublePreferencesKey(Constants.EXPENSE_LIMIT_KEY)

    override suspend fun writeOnboardingKeyToDataStore(completed: Boolean) {
        datastore.edit { store ->
            store[onBoardingKey] = completed
        }
    }

    override suspend fun readOnboardingKeyFromDataStore(): Boolean {
        val preference = datastore.data.first()
        return preference[onBoardingKey] ?: false
    }

    override suspend fun writeCurrencyToDataStore(currency: String) {
        datastore.edit { store ->
            store[selectedCurrency] = currency
        }
    }

    override suspend fun readCurrencyFromDataStore(): Flow<Preferences> {
        return datastore.data
    }

    override suspend fun writeExpenseLimitToDataStore(amount: Double) {
        datastore.edit { store ->
            store[expenseLimit] = amount
        }
    }

    override suspend fun readExpenseLimitFromDataStore(): Flow<Preferences> {
        return datastore.data
    }

    override suspend fun writeLimitKeyToDataStore(enabled: Boolean) {
        datastore.edit { store ->
            store[limitKey] = enabled
        }
    }

    override suspend fun readLimitKeyFromDataStore(): Flow<Preferences> {
        return datastore.data
    }
}