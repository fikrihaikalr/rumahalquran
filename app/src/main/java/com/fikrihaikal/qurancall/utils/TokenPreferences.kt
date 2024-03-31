package com.fikrihaikal.qurancall.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokenDataStore")
class TokenPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = intPreferencesKey("user_id")
        val EMAIL = stringPreferencesKey("email")

    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = "Bearer $token"
        }
    }
    suspend fun saveEmail(email:String){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = email
        }
    }
    suspend fun getEmail(): String?{
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.EMAIL]
    }

    suspend fun saveUserId(userId:Int){
        dataStore.edit { prefereces ->
            prefereces[PreferencesKeys.USER_ID] = userId
        }
    }

    suspend fun getUserId():Int?{
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
    suspend fun deleteId(){
        dataStore.edit {preference ->
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