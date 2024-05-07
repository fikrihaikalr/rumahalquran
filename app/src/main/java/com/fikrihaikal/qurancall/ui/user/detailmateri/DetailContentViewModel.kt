package com.fikrihaikal.qurancall.ui.user.detailmateri

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.detailcontentmateri.DetailContentMateriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailContentViewModel(
    private val materiRepository: MateriRepository,
    private val tokenPreferences: TokenPreferences
) : ViewModel() {
    private var _detailContentResponse = MutableLiveData<Resource<DetailContentMateriResponse>>()
    val detailContentResponse: LiveData<Resource<DetailContentMateriResponse>> get() = _detailContentResponse

    fun getDetailContentMateri(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detailContentResponse.postValue(Resource.Loading())
        try {
            val response =
                tokenPreferences.getToken()?.let { materiRepository.getContentMateriById(it, id) }
            viewModelScope.launch(Dispatchers.Main) {
                if (response!!.payload != null) {
                    _detailContentResponse.postValue(Resource.Success(response.payload))
                } else {
                    _detailContentResponse.postValue(Resource.Error(response.exception, "Error"))
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch(Dispatchers.Main) {
                _detailContentResponse.postValue(Resource.Error(e, "Error"))
            }
        }
    }

}

//    fun getDetailContentMateri(id:String) = viewModelScope.launch(Dispatchers.IO) {
//        _detailContentResponse.postValue(Resource.Loading())
//        try {
//            val response = materiRepository.getContentMateriById(id)
//            viewModelScope.launch(Dispatchers.IO) {
//                Log.i("TEST", response.payload.toString())
//                _detailContentResponse.postValue(Resource.Success(response.payload))
//            }
//        }catch (e: Exception){
//            viewModelScope.launch(Dispatchers.IO) {
//                _detailContentResponse.postValue(Resource.Error(e,e.message))
//            }
//        }
//    }