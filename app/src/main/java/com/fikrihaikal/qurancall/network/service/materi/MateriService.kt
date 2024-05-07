package com.fikrihaikal.qurancall.network.service.materi

import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriBody
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriResponse
import com.fikrihaikal.qurancall.network.model.response.deletemateri.DeleteMateriResponse
import com.fikrihaikal.qurancall.network.model.response.detailcontentmateri.DetailContentMateriResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.submenumateri.SubMenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MateriService {
    @GET("materi")
    suspend fun getAllMenuMateri(
        @Header("Authorization") authorization:String
    ):MenuMateriResponse

    @GET("materi/{materiId}")
    suspend fun getSubMateriById(
        @Header("Authorization") authorization: String,
        @Path("materiId") id: String,
    ):SubMenuMateriResponse

    @GET("submateri/{subMateriId}")
    suspend fun getContentMateriById(
        @Header("Authorization") authorization: String,
        @Path("subMateriId") id:String,
    ):DetailContentMateriResponse

    @POST("category")
    suspend fun postCategory(
        @Header("Authorization") authorization: String,
        @Body tambahKategoriBody: TambahKategoriBody
    ):TambahKategoriResponse

    @DELETE("{materiId}")
    suspend fun deleteMateri(
        @Header("Authorization") authorization: String,
        @Path("materiId") id:String,
    ): DeleteMateriResponse

    @POST("create")
    suspend fun addMateri(
        @Header("Authorization") authorization: String,
        @Body addMateriBody: AddMateriBody
    ): AddMateriResponse
}