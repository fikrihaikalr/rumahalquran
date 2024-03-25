package com.fikrihaikal.qurancall.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register : LiveData<Resource<RegisterResponse>> get() = _register

    fun register(registerBody: RegisterBody){
        _register.postValue(Resource.Loading())
        viewModelScope.launch {
            _register.postValue(dataRepository.postRegist(registerBody))
        }
    }
}