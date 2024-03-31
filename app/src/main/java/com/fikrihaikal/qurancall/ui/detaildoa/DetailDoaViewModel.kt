package com.fikrihaikal.qurancall.ui.detaildoa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.launch

class DetailDoaViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _detailDoa = MutableLiveData<Resource<DetailDoaResponse>>()
    val detailDoa: LiveData<Resource<DetailDoaResponse>> get() = _detailDoa

    fun getDetailDoa(token:String,id:String){
        viewModelScope.launch {
            _detailDoa.postValue(dataRepository.getDetailDoa(token, id))
        }
    }
}