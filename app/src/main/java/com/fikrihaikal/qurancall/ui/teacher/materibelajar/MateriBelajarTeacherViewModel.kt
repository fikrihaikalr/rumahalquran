package com.fikrihaikal.qurancall.ui.teacher.materibelajar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.deletemateri.DeleteMateriResponse
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MateriBelajarTeacherViewModel(private val materiRepository: MateriRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _listMateriResponse = MutableLiveData<Resource<MenuMateriResponse>>(Resource.Loading())
    val listMateriResponse: LiveData<Resource<MenuMateriResponse>> get() = _listMateriResponse

    private var _deleteMateriResponse = MutableLiveData<Resource<DeleteMateriResponse>>(Resource.Loading())
    val deleteMateriResponse: LiveData<Resource<DeleteMateriResponse>> get() = _deleteMateriResponse

    fun getListMenuMateri(){
        viewModelScope.launch(Dispatchers.IO) {
            _listMateriResponse.postValue(materiRepository.getListMenuMateri(tokenPreferences.getToken().orEmpty()))
            Log.d("menuMateriTeacherViewModel",materiRepository.getListMenuMateri(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
    }

    fun deleteMateriById(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenPreferences.getToken().orEmpty()
            _deleteMateriResponse.postValue(Resource.Loading())
            val result = materiRepository.deleteMateri(token, id)
            _deleteMateriResponse.postValue(result)
            if (result is Resource.Success){
                getListMenuMateri()
            }
        }
    }
}