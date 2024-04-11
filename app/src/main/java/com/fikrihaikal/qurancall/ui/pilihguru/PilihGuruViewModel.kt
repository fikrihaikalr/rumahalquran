package com.fikrihaikal.qurancall.ui.pilihguru

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.launch

class PilihGuruViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _listGuruResponse = MutableLiveData<Resource<GuruResponse>>(Resource.Loading())
    val listGuruResponse: LiveData<Resource<GuruResponse>> get() = _listGuruResponse

    fun getListGuru() {
        viewModelScope.launch {
            _listGuruResponse.postValue( dataRepository.getListGuru())
            Log.d("guru",dataRepository.getListGuru().payload?.data.toString())
        }
    }
}