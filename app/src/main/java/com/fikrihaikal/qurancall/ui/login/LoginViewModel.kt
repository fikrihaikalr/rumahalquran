package com.fikrihaikal.qurancall.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    fun login(loginBody:LoginBody){
        _login.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val response = dataRepository.postLogin(loginBody)
            viewModelScope.launch(Dispatchers.Main){
                _login.postValue(response)
            }
        }
    }

    fun saveIdNSaveToken(token:String,userId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.saveToken(token)
            tokenPreferences.saveUserId(userId)
        }
    }
}