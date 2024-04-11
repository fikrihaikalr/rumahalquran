package com.fikrihaikal.qurancall.network.service.surah

import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import retrofit2.http.GET

interface SurahService {
    @GET("api/v2/surat")
    suspend fun getAllSurah(): SurahResponse
}