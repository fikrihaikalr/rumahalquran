package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fikrihaikal.qurancall.data.paging.DoaPagingSource
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.network.service.ApiConfig
import com.fikrihaikal.qurancall.network.service.ApiService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed
import kotlinx.coroutines.flow.Flow

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

    fun getDoa(): Flow<PagingData<DataItem>> =
        Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory ={DoaPagingSource(apiService,context)}
        ).flow

    suspend fun getDetailDoa(token: String, id: String): Resource<DetailDoaResponse> =
        proceed {
            ApiConfig.getApiService().getDetailDoa(token, id)
        }
}

//    suspend fun getListDoa():Resource<DoaResponse> {
//        Log.d("repo doa",apiService.getListDoa().data.toString())
//        return proceed {
//            apiService.getListDoa()
//        }
//    }

//    fun getDoaList(): Flow<PagingData<DoaItem>> =
//        Pager(
//            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//            pagingSourceFactory = { DoaPagingSource(apiService, context) }
//        ).flow