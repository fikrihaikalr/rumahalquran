package com.fikrihaikal.qurancall.ui.user.gantipassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.domain.model.user.resetpassword.UserResetPassword
import com.fikrihaikal.qurancall.domain.usecase.resetpassword.ValidateUserUseCaseResetPassword
import com.fikrihaikal.qurancall.domain.usecase.resetpassword.ValidationResultResetPassword
import com.fikrihaikal.qurancall.network.model.response.resetpassword.ResetPasswordBody
import com.fikrihaikal.qurancall.network.model.response.resetpassword.ResetPasswordResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GantiPasswordViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _editProfile = MutableLiveData<Resource<GetUserResponse>>()
    val editProfile : LiveData<Resource<GetUserResponse>> get() = _editProfile
    private val _updatePassword = MutableLiveData<Resource<ResetPasswordResponse>>()
    val updatePassword: LiveData<Resource<ResetPasswordResponse>> get() = _updatePassword

    private val validateUserUseCaseResetPassword = ValidateUserUseCaseResetPassword()
    val validationResultResetPassword = MutableLiveData<ValidationResultResetPassword>()

    fun validateResetPassword(user: UserResetPassword){
        val result = validateUserUseCaseResetPassword.validate(user)
        validationResultResetPassword.value = result
    }
    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _editProfile.postValue(dataRepository.getUser(tokenPreferences.getToken().orEmpty(), tokenPreferences.getUserId()!!))
        }
    }

    fun clearState() {
        _updatePassword.value = null
    }
    fun resetPassword(resetPasswordBody: ResetPasswordBody){
        _updatePassword.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = dataRepository.updatePassword(tokenPreferences.getToken().orEmpty(),tokenPreferences.getUserId().toString(),resetPasswordBody)
            viewModelScope.launch(Dispatchers.Main) {
                _updatePassword.postValue(response)
            }
        }
    }
}