package com.fikrihaikal.qurancall.di

import android.content.Context
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.service.ApiConfig

object Injection{
    fun provideRepository(context:Context): DataRepository{
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService, context)
    }
}