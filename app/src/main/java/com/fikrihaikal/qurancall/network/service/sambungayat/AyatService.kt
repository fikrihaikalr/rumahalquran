package com.fikrihaikal.qurancall.network.service.sambungayat

import com.fikrihaikal.qurancall.network.model.response.sambungayat.AyatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AyatService {

    @GET("v2/surat/{number}")
    fun getAyat(@Path("number") number: Int): Call<AyatResponse>
}