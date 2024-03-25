package com.fikrihaikal.qurancall.network.service.adzan


import com.fikrihaikal.qurancall.network.model.response.adzan.DataKotaResponse
import com.fikrihaikal.qurancall.network.model.response.adzan.WaktuAdzanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AdzanApiService{

    @GET("sholat/kota/semua")
    fun getAllCities(): Call<DataKotaResponse>

    @GET("sholat/kota/cari/{query}")
    fun searchCity(@Path("query") query: String): Call<DataKotaResponse>

    @GET("sholat/jadwal/{cityId}/{year}/{month}/{day}")
    fun getPrayerTimes(
        @Path("cityId") cityId: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Call<WaktuAdzanResponse>
}