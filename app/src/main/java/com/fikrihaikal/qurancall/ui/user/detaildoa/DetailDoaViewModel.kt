package com.fikrihaikal.qurancall.ui.user.detaildoa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.detaildoa.DetailDoaResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailDoaViewModel(private val dataRepository: DataRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _detailDoaResponse = MutableLiveData<Resource<DetailDoaResponse>>()
    val detailDoaResponse : LiveData<Resource<DetailDoaResponse>> get() = _detailDoaResponse
    fun getDetailDoa(id:String) = viewModelScope.launch(Dispatchers.IO) {
        _detailDoaResponse.postValue(Resource.Loading())
        try {
            val response = tokenPreferences.getToken()?.let { dataRepository.getDetailDoa(it,id) }
            viewModelScope.launch(Dispatchers.Main) {
                if (response!!.payload != null){
                    _detailDoaResponse.postValue(Resource.Success(response.payload))
                }else{
                    _detailDoaResponse.postValue(Resource.Error(response.exception,"Error"))
                }
            }
        }catch (e:Exception){
            viewModelScope.launch(Dispatchers.Main) {
                _detailDoaResponse.postValue(Resource.Error(e,"Error"))
            }
        }
    }

//    fun getDetailDoa(token:String,id:String){
//        viewModelScope.launch {
//            _detailDoa.postValue(dataRepository.getDetailDoa(token, id))
//        }
//    }
}