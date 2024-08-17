package com.fikrihaikal.qurancall.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.domain.model.user.register.User
import com.fikrihaikal.qurancall.domain.usecase.register.ValidateUserUseCase
import com.fikrihaikal.qurancall.domain.usecase.register.ValidationResult
import com.fikrihaikal.qurancall.network.model.response.register.RegisterBody
import com.fikrihaikal.qurancall.network.model.response.register.RegisterResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register : LiveData<Resource<RegisterResponse>> get() = _register

    private val validateUserUseCase = ValidateUserUseCase()
    val validationResult = MutableLiveData<ValidationResult>()

    fun validateUser(user: User){
        val result = validateUserUseCase.validate(user)
        validationResult.value = result
    }
    fun register(registerBody: RegisterBody){
        _register.postValue(Resource.Loading())
        viewModelScope.launch {
            _register.postValue(dataRepository.postRegist(registerBody))
        }
    }
}