package com.fikrihaikal.qurancall.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.runBlocking
import javax.security.auth.callback.Callback


class HomeViewModel(private val dataRepository: DataRepository): ViewModel() {
    //    private val _userResponse = MutableLiveData<Resource<GetUserResponse>>()
//    val userResponse: LiveData<Resource<GetUserResponse>> get() = _userResponse
//
//    fun getUser(userId:Int){
//        viewModelScope.launch {
//            _userResponse.value = dataRepository.getUser(userId)
//        }
//    }
}

