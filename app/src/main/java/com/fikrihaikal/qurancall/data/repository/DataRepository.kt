package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.network.service.api.ApiService
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

    suspend fun getListDoa():Resource<DoaResponse> =
        proceed {
            apiService.getListDoa()
        }

    suspend fun getDetailDoa(id:String): Resource<DetailDoaResponse> =
        proceed {
            apiService.getDetailDoa(id)
        }

    suspend fun getListGuru():Resource<GuruResponse> =
        proceed {
            apiService.getListGuru()
        }
    suspend fun getDetailGuru(id:String): Resource<DetailGuruResponse> =
        proceed {
            apiService.getDetailGuru(id)
        }
//    suspend fun getDetailGuru(id: String):Resource<DetailGuruResponse> =
//        proceed {
//            ApiConfig.getApiService().getDetailGuru(id)
//        }

}

//suspend fun getListDoa():Resource<DoaResponse> {
//    Log.d("repo doa",apiService.getListDoa().data.toString())
//    return proceed {
//        apiService.getListDoa()
//    }
//}

//    fun getDoaList(): Flow<PagingData<DoaItem>> =
//        Pager(
//            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//            pagingSourceFactory = { DoaPagingSource(apiService, context) }
//        ).flow