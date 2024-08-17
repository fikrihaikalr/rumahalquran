package com.fikrihaikal.qurancall.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokenDataStore")

class TokenPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = intPreferencesKey("user_id")
        val USER_ROLES = stringPreferencesKey("user_roles")
        val ON_BOARDING = stringPreferencesKey("on_boarding")
    }

    suspend fun saveUserRoles(roles: List<String?>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ROLES] = Gson().toJson(roles)
        }
    }



     fun getUserRoles(): Flow<String> {
        return dataStore.data.map {
            it[PreferencesKeys.USER_ROLES].orEmpty()
        }
    }

    suspend fun deleteUserRoles() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_ROLES)
        }
    }

     fun getOnBoarding() :Flow<String>{
        return dataStore.data.map {
            it[PreferencesKeys.ON_BOARDING].orEmpty()
        }
    }

   suspend fun saveOnBoarding(doneOnBoarding : String) {
        dataStore.edit {
            it[PreferencesKeys.ON_BOARDING] = doneOnBoarding
        }
    }
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = "Bearer $token"
        }
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { prefereces ->
            prefereces[PreferencesKeys.USER_ID] = userId
        }
    }



    suspend fun getUserId(): Int? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.USER_ID]
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.TOKEN]
    }


    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
        }

    }

    suspend fun deleteId() {
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.USER_ID)
        }
    }


    companion object {

        fun getTokenSynchronously(dataStore: DataStore<Preferences>): String? {
            return runBlocking {
                TokenPreferences(dataStore).getToken()
            }
        }

        @Volatile
        private var INSTANCE: TokenPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}