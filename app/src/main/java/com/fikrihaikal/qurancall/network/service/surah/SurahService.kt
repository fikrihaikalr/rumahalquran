package com.fikrihaikal.qurancall.network.service.surah

import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SurahService {
    @GET("surah")
    suspend fun getAllSurah(): SurahResponse

    @GET("surah/{surahId}")
    suspend fun getDetailSurah(
        @Path("surahId") id: String,
    ): DetailSurahResponse
}