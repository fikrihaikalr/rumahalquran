package com.fikrihaikal.qurancall.ui.auth.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.forgotpassword.ForgotPasswordBody
import com.fikrihaikal.qurancall.network.model.response.forgotpassword.ForgotPasswordResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _forgotPasswordResponse = MutableLiveData<Resource<ForgotPasswordResponse>>()
    val forgotPasswordResponse: LiveData<Resource<ForgotPasswordResponse>> get() = _forgotPasswordResponse

    fun forgotPassword(forgotPasswordBody: ForgotPasswordBody){
        _forgotPasswordResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val response = dataRepository.postForgotPassword(forgotPasswordBody)
            viewModelScope.launch(Dispatchers.Main){
                _forgotPasswordResponse.postValue(response)
            }
        }
    }
    fun clearState() {
        _forgotPasswordResponse.value = null
    }
}