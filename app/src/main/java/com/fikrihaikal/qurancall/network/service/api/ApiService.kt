package com.fikrihaikal.qurancall.network.service.api

import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @POST("signup")
    suspend fun postRegist(@Body registerBody: RegisterBody
    ): RegisterResponse

    @POST("signin")
    suspend fun postLogin(@Body loginBody: LoginBody
    ): LoginResponse

    @GET("user/{userId}")
    suspend fun getUser(
        @Header("Authorization") authorization:String,
        @Path("userId") userId: Int
    ): GetUserResponse

    @GET("list")
    suspend fun getListDoa(
        @Header("Authorization") authorization:String
    ): DoaResponse

    @GET("doa/{id}")
    suspend fun getDetailDoa(
        @Header("Authorization") authorization:String,
        @Path("id") id:String,
    ): DetailDoaResponse

    @GET("guru")
    suspend fun getListGuru(
        @Header("Authorization") authorization: String
    ):GuruResponse

    @GET("guru/{userId}")
    suspend fun getDetailGuru(
        @Header("Authorization") authorization: String,
        @Path("userId") id:String,
    ):DetailGuruResponse



}

//    @GET("listDoa")
//    suspend fun getListDoa(): DoaResponse
//@GET("listDoa")
//suspend fun getListDoa() : ListDoaResponse
//@GET("listDoa")
//suspend fun getListDoa(
//    @Header("Authorization") token:String,
//    @Query("page") page:Int? = DEFAULT_PAGE,
//): ListDoaResponse
//
//companion object{
//    const val DEFAULT_PAGE = 1
//}

