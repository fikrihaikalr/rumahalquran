package com.fikrihaikal.qurancall.network.service.materi

import com.fikrihaikal.qurancall.BuildConfig
import com.fikrihaikal.qurancall.network.service.surah.SurahService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MateriConfig {

    companion object{
        fun getMateriService(): MateriService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(MateriService::class.java)
        }
    }
}