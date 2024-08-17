package com.fikrihaikal.qurancall.ui.user.fotoprofile.uploadfoto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.uploadfoto.UploadPhotoResponse
import com.fikrihaikal.qurancall.network.model.response.user.GetUserResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadFotoViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _uploadPhotoResponse = MutableLiveData<Resource<UploadPhotoResponse>>()
    val uploadPhotoResponse: LiveData<Resource<UploadPhotoResponse>> get() = _uploadPhotoResponse
    private val _userResponse = MutableLiveData<Resource<GetUserResponse>>()
    val userResponse: LiveData<Resource<GetUserResponse>> get() = _userResponse

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _userResponse.postValue(dataRepository.getUser(tokenPreferences.getToken().orEmpty(), tokenPreferences.getUserId()!!))
        }
    }

    fun uploadPhoto(file:MultipartBody.Part){
        _uploadPhotoResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _uploadPhotoResponse.postValue(dataRepository.uploadPhoto(tokenPreferences.getToken().orEmpty(),file,tokenPreferences.getUserId().toString()))
        }
    }
}