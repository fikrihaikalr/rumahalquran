package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import com.fikrihaikal.qurancall.network.model.response.doa.DoaItem
import com.fikrihaikal.qurancall.network.model.response.doa.ListDoaResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.network.service.ApiService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed

class DataRepository(private val  apiService: ApiService, private val context: Context){

    suspend fun postLogin(loginBody: LoginBody): Resource<LoginResponse> =
        proceed {
            apiService.postLogin(loginBody)
        }

    suspend fun postRegist(registerBody: RegisterBody):Resource<RegisterResponse> =
        proceed {
            apiService.postRegist(registerBody)
        }

    suspend fun getUser(userId:Int):Resource<GetUserResponse> =
        proceed {
            apiService.getUser(userId)
        }
    suspend fun getListDoa():Resource<ListDoaResponse> =
        proceed {
            apiService.getListDoa()
        }
}

//    fun getDoaList(): Flow<PagingData<DoaItem>> =
//        Pager(
//            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//            pagingSourceFactory = { DoaPagingSource(apiService, context) }
//        ).flow