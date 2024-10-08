package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.network.model.response.forgotpassword.ForgotPasswordBody
import com.fikrihaikal.qurancall.network.model.response.forgotpassword.ForgotPasswordResponse
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.network.model.response.resetpassword.ResetPasswordBody
import com.fikrihaikal.qurancall.network.model.response.resetpassword.ResetPasswordResponse
import com.fikrihaikal.qurancall.network.model.response.uploadfoto.UploadPhotoResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.network.service.api.ApiService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed
import okhttp3.MultipartBody

class DataRepository(private val  apiService: ApiService, private val context: Context){

    suspend fun postLogin(loginBody: LoginBody): Resource<LoginResponse> =
        proceed {
            apiService.postLogin(loginBody)
        }

    suspend fun postRegist(registerBody: RegisterBody):Resource<RegisterResponse> =
        proceed {
            apiService.postRegist(registerBody)
        }

    suspend fun postForgotPassword(forgotPasswordBody: ForgotPasswordBody):Resource<ForgotPasswordResponse> =
        proceed {
            apiService.postForgotPassword(forgotPasswordBody)
        }
    suspend fun getUser(token:String,userId:Int):Resource<GetUserResponse> =
        proceed {
            apiService.getUser(token,userId)
        }

    suspend fun getListDoa(token:String):Resource<DoaResponse> =
        proceed {
            apiService.getListDoa(token)
        }

    suspend fun getDetailDoa(token: String,id:String): Resource<DetailDoaResponse> =
        proceed {
            apiService.getDetailDoa(token,id)
        }

    suspend fun getListGuru(token: String):Resource<GuruResponse> =
        proceed {
            apiService.getListGuru(token)
        }
    suspend fun getDetailGuru(token: String,id:String): Resource<DetailGuruResponse> =
        proceed {
            apiService.getDetailGuru(token,id)
        }

    suspend fun updatePassword(token: String, id:String, resetPasswordBody: ResetPasswordBody) : Resource<ResetPasswordResponse> =
        proceed {
            apiService.resetPassword(token,id,resetPasswordBody)
        }

    suspend fun uploadPhoto(token: String,file:MultipartBody.Part,id: String):Resource<UploadPhotoResponse> =
        proceed {
            apiService.uploadPhoto(token,file,id)
        }

}

//    suspend fun getDetailGuru(id: String):Resource<DetailGuruResponse> =
//        proceed {
//            ApiConfig.getApiService().getDetailGuru(id)
//        }

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