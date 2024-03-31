package com.fikrihaikal.qurancall.di

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.service.ApiConfig
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore

object Injection{
    fun provideRepository(context:Context): DataRepository{
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService, context)
    }

    fun provideDataStore(context: Context): TokenPreferences{
        val dataStore: DataStore<Preferences> = context.dataStore
        val tokenPreferences = TokenPreferences.getInstance(dataStore)
        return tokenPreferences
    }
}