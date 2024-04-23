package com.fikrihaikal.qurancall.ui.teacher.detailsurahteacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailSurahTeacherViewModel(private val surahRepository: SurahRepository) : ViewModel() {
    private var _detailSurahResponse = MutableLiveData<Resource<DetailSurahResponse>>()
    val detailsurahResponse : LiveData<Resource<DetailSurahResponse>> get() = _detailSurahResponse

    fun getDetail(id:String) = viewModelScope.launch(Dispatchers.IO) {
        _detailSurahResponse.postValue(Resource.Loading())
        try {
            val response = surahRepository.getDetailSurah(id)
            viewModelScope.launch(Dispatchers.Main) {
                _detailSurahResponse.postValue(Resource.Success(response.payload))
            }
        }catch (e: Exception){
            viewModelScope.launch(Dispatchers.Main) {
                _detailSurahResponse.postValue(Resource.Error(e,e.message))
            }
        }
    }
}