package com.fikrihaikal.qurancall.ui.teacher.detailmateriteacher

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

class DetailContentTeacherViewModel(
    private val materiRepository: MateriRepository,
    private val tokenPreferences: TokenPreferences
) : ViewModel() {
    private var _detailContentTeacherResponse = MutableLiveData<Resource<DetailContentMateriResponse>>()
    val detailContentTeacherResponse: LiveData<Resource<DetailContentMateriResponse>> get() = _detailContentTeacherResponse

    fun getDetailContentMateri(id:String) = viewModelScope.launch(Dispatchers.IO) {
        _detailContentTeacherResponse.postValue(Resource.Loading())
        try {
            val response =
                tokenPreferences.getToken()?.let { materiRepository.getContentMateriById(it,id) }
            viewModelScope.launch(Dispatchers.Main) {
                if (response!!.payload != null){
                    _detailContentTeacherResponse.postValue(Resource.Success(response.payload))
                }else{
                    _detailContentTeacherResponse.postValue(Resource.Error(response.exception,"Error"))
                }
            }
        }catch (e: Exception){
            viewModelScope.launch(Dispatchers.Main) {
                _detailContentTeacherResponse.postValue(Resource.Error(e,"Error"))
            }
        }
    }
}