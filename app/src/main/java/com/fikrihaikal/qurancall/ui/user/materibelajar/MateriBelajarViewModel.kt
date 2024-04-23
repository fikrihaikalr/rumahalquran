package com.fikrihaikal.qurancall.ui.user.materibelajar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MateriBelajarViewModel(private val materiRepository: MateriRepository) : ViewModel() {
    private var _listMateriResponse = MutableLiveData<Resource<MenuMateriResponse>>(Resource.Loading())
    val listMateriResponse: LiveData<Resource<MenuMateriResponse>> get() = _listMateriResponse

    fun getListMenuMateri(){
        viewModelScope.launch(Dispatchers.IO) {
            _listMateriResponse.postValue(materiRepository.getListMenuMateri())
            Log.d("menuMateriViewModel",materiRepository.getListMenuMateri().payload?.data.toString())
        }
    }
}