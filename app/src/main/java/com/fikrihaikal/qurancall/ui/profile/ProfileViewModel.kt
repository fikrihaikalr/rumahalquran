package com.fikrihaikal.qurancall.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _profile = MutableLiveData<Resource<GetUserResponse>>()
    val profile :LiveData<Resource<GetUserResponse>> get() = _profile

    fun getUser(userId:Int){
      viewModelScope.launch {
          _profile.value = dataRepository.getUser(userId)
      }
    }
}