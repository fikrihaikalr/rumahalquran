package com.fikrihaikal.qurancall.ui.teacher.uploadmateri

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriBody
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriResponse
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UploadMateriViewModel(private val materiRepository: MateriRepository, private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _listMateriResponse = MutableLiveData<Resource<MenuMateriResponse>>(Resource.Loading())
    val listMateriResponse: LiveData<Resource<MenuMateriResponse>> get() = _listMateriResponse

    private val _addMateri = MutableLiveData<Resource<AddMateriResponse>>()
    val addMateri : LiveData<Resource<AddMateriResponse>> get() = _addMateri


    fun getListMenuMateri(){
        viewModelScope.launch(Dispatchers.IO) {
            _listMateriResponse.postValue(materiRepository.getListMenuMateri(tokenPreferences.getToken().orEmpty()))
            Log.d("menuMateriTeacherViewModel",materiRepository.getListMenuMateri(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
    }

    fun postMateri(addMateriBody: AddMateriBody){
        _addMateri.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = materiRepository.addMateri(tokenPreferences.getToken().orEmpty(),addMateriBody)
            viewModelScope.launch(Dispatchers.Main) {
                _addMateri.postValue(response)
            }
        }
    }
}