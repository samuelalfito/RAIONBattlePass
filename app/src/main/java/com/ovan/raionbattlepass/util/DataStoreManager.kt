package com.ovan.raionbattlepass.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStores: DataStore<Preferences>) {
    private companion object {
        val TOKEN = stringPreferencesKey("token")
    }
    
    val token: Flow<String> = dataStores.data.map { preferences ->
        preferences[TOKEN] ?: ""
    }
    
    suspend fun setToken(token: String) {
        dataStores.edit { preferences ->
            preferences[TOKEN] = token
        }
    }
}
