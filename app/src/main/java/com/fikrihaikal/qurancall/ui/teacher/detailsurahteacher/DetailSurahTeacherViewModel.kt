package com.fikrihaikal.qurancall.ui.teacher.detailsurahteacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailSurahTeacherViewModel(private val surahRepository: SurahRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _detailSurahResponse = MutableLiveData<Resource<DetailSurahResponse>>()
    val detailsurahResponse : LiveData<Resource<DetailSurahResponse>> get() = _detailSurahResponse

//    fun getDetail(id:String) = viewModelScope.launch(Dispatchers.IO) {
//        _detailSurahResponse.postValue(Resource.Loading())
//        try {
//            val response = surahRepository.getDetailSurah(id)
//            viewModelScope.launch(Dispatchers.Main) {
//                _detailSurahResponse.postValue(Resource.Success(response.payload))
//            }
//        }catch (e: Exception){
//            viewModelScope.launch(Dispatchers.Main) {
//                _detailSurahResponse.postValue(Resource.Error(e,e.message))
//            }
//        }
//    }
fun getDetail(id:String) = viewModelScope.launch(Dispatchers.IO) {
    _detailSurahResponse.postValue(Resource.Loading())
    try {
        val response = tokenPreferences.getToken()?.let { surahRepository.getDetailSurah(it,id) }
        viewModelScope.launch(Dispatchers.Main) {
            if (response!!.payload != null){
                _detailSurahResponse.postValue(Resource.Success(response.payload))
            }else{
                _detailSurahResponse.postValue(Resource.Error(response.exception,"Error"))
            }
        }
    }catch (e:Exception){
        viewModelScope.launch(Dispatchers.Main) {
            _detailSurahResponse.postValue(Resource.Error(e,"Error"))
        }
    }
}
}