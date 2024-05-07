package com.fikrihaikal.qurancall.ui.user.pilihguru

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.launch

class PilihGuruViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {

    private val _listGuruResponse = MutableLiveData<Resource<GuruResponse>>(Resource.Loading())
    val listGuruResponse: LiveData<Resource<GuruResponse>> get() = _listGuruResponse

    fun getListGuru() {
        viewModelScope.launch {
            _listGuruResponse.postValue( dataRepository.getListGuru(tokenPreferences.getToken().orEmpty()))
            Log.d("guru",dataRepository.getListGuru(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
    }
}