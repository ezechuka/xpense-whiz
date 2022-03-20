package com.javalon.xpensewhiz.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "onboarding_key_store")
class DatastoreRepositoryImpl @Inject constructor(context: Context): DatastoreRepository {

    private val onBoardingKey = booleanPreferencesKey(Constants.WELCOME_KEY)
    private val datastore = context.datastore

    override suspend fun writeToDataStore(completed: Boolean) {
        datastore.edit { store ->
            store[onBoardingKey] = completed
        }
    }

    override suspend fun readFromDataStore(): Boolean {
        val preference = datastore.data.first()
        return preference[onBoardingKey] ?: false
    }
}