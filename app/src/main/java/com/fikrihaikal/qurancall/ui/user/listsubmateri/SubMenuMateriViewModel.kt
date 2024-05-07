package com.fikrihaikal.qurancall.ui.user.listsubmateri

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.submenumateri.SubMenuMateriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubMenuMateriViewModel(private val materiRepository: MateriRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _subMenuMateriResponse = MutableLiveData<Resource<SubMenuMateriResponse>>()
    val subMenuMateriResponse : LiveData<Resource<SubMenuMateriResponse>> get() = _subMenuMateriResponse

//    fun getSubMenuMateri(id:String) = viewModelScope.launch(Dispatchers.IO) {
//        _subMenuMateriResponse.postValue(Resource.Loading())
//        try {
//            val response = materiRepository.getSubMateriById(id)
//            viewModelScope.launch(Dispatchers.IO) {
//                Log.i("TEST", response.payload.toString())
//                _subMenuMateriResponse.postValue(Resource.Success(response.payload))
//            }
//        }catch (e: Exception){
//            viewModelScope.launch(Dispatchers.IO) {
//                _subMenuMateriResponse.postValue(Resource.Error(e,e.message))
//            }
//        }
//    }

    fun getSubMenuMateri(id:String) = viewModelScope.launch(Dispatchers.IO) {
        _subMenuMateriResponse.postValue(Resource.Loading())
        try {
            val response = tokenPreferences.getToken()?.let { materiRepository.getSubMateriById(it,id) }
            viewModelScope.launch(Dispatchers.Main) {
                if (response!!.payload != null){
                    _subMenuMateriResponse.postValue(Resource.Success(response.payload))
                }else{
                    _subMenuMateriResponse.postValue(Resource.Error(response.exception,"Error"))
                }
            }
        }catch (e:Exception){
            viewModelScope.launch(Dispatchers.Main) {
                _subMenuMateriResponse.postValue(Resource.Error(e,"Error"))
            }
        }
    }
}