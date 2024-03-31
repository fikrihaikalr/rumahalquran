package com.fikrihaikal.qurancall.utils

import android.net.http.HttpException
import com.fikrihaikal.qurancall.network.model.response.error.ResponseError
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

sealed class Resource<T> private constructor(
    val payload: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Success<T>(val data: T?) : Resource<T>(data)
    class Error<T>(exception: Exception?, message: String?) :
        Resource<T>(message = message, exception = exception)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}

suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(coroutine.invoke())
    } catch (exception: Exception) {
        when (exception) {
            is retrofit2.HttpException -> {
                val errorMessageResponseType = object : TypeToken<ResponseError>() {}.type
                val error: ResponseError = Gson().fromJson(
                    exception.response()?.errorBody()?.charStream(),
                    errorMessageResponseType
                )
                Resource.Error(exception, error.message)
            }
            else -> {
                Resource.Error(exception, exception.message)
            }
        }
    }
}