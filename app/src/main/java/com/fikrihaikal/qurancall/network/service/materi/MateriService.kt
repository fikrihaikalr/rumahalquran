package com.fikrihaikal.qurancall.network.service.materi

import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import retrofit2.http.GET

interface MateriService {

    @GET("materi")
    suspend fun getAllMenuMateri():MenuMateriResponse
}