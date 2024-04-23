package com.fikrihaikal.qurancall.ui.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _profile = MutableLiveData<Resource<GetUserResponse>>()
    val profile :LiveData<Resource<GetUserResponse>> get() = _profile

    fun getUser(){
      viewModelScope.launch(Dispatchers.IO){
          _profile.postValue(tokenPreferences.getUserId()?.let { dataRepository.getUser(it) })
      }
    }
    fun deleteIdNDeleteToken(){
        viewModelScope.launch(Dispatchers.IO) {
            tokenPreferences.deleteToken()
            tokenPreferences.deleteId()
            tokenPreferences.deleteUserRoles()
        }
    }
}