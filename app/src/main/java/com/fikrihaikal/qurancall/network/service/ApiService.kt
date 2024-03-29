package com.fikrihaikal.qurancall.network.service

import com.fikrihaikal.qurancall.network.model.response.doa.ListDoaResponse
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
    suspend fun getUser(@Path("userId") userId: Int): GetUserResponse

    @GET("listDoa")
    suspend fun getListDoa(): ListDoaResponse

}
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

