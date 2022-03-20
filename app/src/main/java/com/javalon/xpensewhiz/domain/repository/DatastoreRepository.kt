package com.javalon.xpensewhiz.domain.repository

interface DatastoreRepository {
    suspend fun writeToDataStore(completed: Boolean)

    suspend fun readFromDataStore(): Boolean
}