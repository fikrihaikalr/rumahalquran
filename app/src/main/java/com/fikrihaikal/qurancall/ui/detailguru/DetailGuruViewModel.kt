package com.fikrihaikal.qurancall.ui.detailguru

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.network.model.response.detailguru.DetailGuruResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailGuruViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _detailGuruResponse = MutableLiveData<Resource<DetailGuruResponse>>()
    val detailGuruResponse: LiveData<Resource<DetailGuruResponse>> get() = _detailGuruResponse

//    fun getDetailGuru(id:String){
//        viewModelScope.launch {
//            _detailGuruResponse.value = Resource.Loading()
//            val response = dataRepository.getDetailGuru(id)
//            _detailGuruResponse.value = response
//        }
//    }
    fun getDetail(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailGuruResponse.postValue(Resource.Loading())
        try {
            val response = dataRepository.getDetailGuru(id)
            viewModelScope.launch(Dispatchers.Main) {
                if (response.payload != null) {
                    _detailGuruResponse.postValue(Resource.Success(response.payload))
                } else {
                    _detailGuruResponse.postValue(Resource.Error(response.exception, "Error"))

                }
            }
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _detailGuruResponse.postValue(Resource.Error(e, "Error"))
            }
        }
    }
}