package com.fikrihaikal.qurancall.ui.teacher.doateacher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.launch

class DoaTeacherViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _listDoaResponse = MutableLiveData<Resource<DoaResponse>>(Resource.Loading())
    val listDoaResponse: LiveData<Resource<DoaResponse>> get() = _listDoaResponse

    fun getListDoa() {
        viewModelScope.launch {
            _listDoaResponse.postValue( dataRepository.getListDoa(tokenPreferences.getToken().orEmpty()))
            Log.d("doa",dataRepository.getListDoa(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
    }
}