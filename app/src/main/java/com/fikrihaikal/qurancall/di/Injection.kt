package com.fikrihaikal.qurancall.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.service.api.ApiConfig
import com.fikrihaikal.qurancall.network.service.materi.MateriConfig
import com.fikrihaikal.qurancall.network.service.surah.SurahConfig
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

    fun provideSurahRepository(context: Context):SurahRepository{
        val surahService = SurahConfig.getSurahService()
        return SurahRepository(surahService,context)
    }

    fun provideMateriRepository(context: Context):MateriRepository{
        val materiService = MateriConfig.getMateriService()
        return MateriRepository(materiService,context)
    }


}