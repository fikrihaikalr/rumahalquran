package com.fikrihaikal.qurancall.ui.teacher.hometeacher

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

class HomeTeacherViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _userResponse = MutableLiveData<Resource<GetUserResponse>>()
    val userResponse: LiveData<Resource<GetUserResponse>> get() = _userResponse

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _userResponse.postValue(tokenPreferences.getUserId()?.let { dataRepository.getUser(it) })
        }
    }
}