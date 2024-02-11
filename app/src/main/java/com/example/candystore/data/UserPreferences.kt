package com.example.candystore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStores by preferencesDataStore("data_store")
class UserPreferences(
    context: Context
) {

    private val applicationContext: Context = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.dataStores

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit {preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }



    companion object {
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }

}