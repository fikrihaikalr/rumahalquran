package com.fikrihaikal.qurancall.ui.teacher.gantipasswordteacher

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

class GantiPasswordTeacherViewModel(private val dataRepository: DataRepository, private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _editProfile = MutableLiveData<Resource<GetUserResponse>>()
    val editProfile : LiveData<Resource<GetUserResponse>> get() = _editProfile

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _editProfile.postValue(dataRepository.getUser(tokenPreferences.getToken().orEmpty(), tokenPreferences.getUserId()!!))
        }
    }
}