package com.fikrihaikal.qurancall.ui.teacher.submateriteacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.deletsubmateri.DeleteSubMateriResponse
import com.fikrihaikal.qurancall.network.model.response.submenumateri.SubMenuMateriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubMenuMateriTeacherViewModel(private val materiRepository: MateriRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _subMenuMateriTeacherResponse = MutableLiveData<Resource<SubMenuMateriResponse>>()
    val subMenuMateriTeacherResponse: LiveData<Resource<SubMenuMateriResponse>> get() = _subMenuMateriTeacherResponse

    private var _deleteSubMateriResponse = MutableLiveData<Resource<DeleteSubMateriResponse>>(Resource.Loading())
    val deleteSubMateriResponse: LiveData<Resource<DeleteSubMateriResponse>> get() = _deleteSubMateriResponse
    fun deleteSubMateriById(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val token = tokenPreferences.getToken().orEmpty()
            _deleteSubMateriResponse.postValue(Resource.Loading())
            val result = materiRepository.deleteSubMateri(token, id)
            _deleteSubMateriResponse.postValue(result)
        }
    }
    fun getSubMenuMateriTeacher(id:String) = viewModelScope.launch(Dispatchers.IO) {
        _subMenuMateriTeacherResponse.postValue(Resource.Loading())
        try {
            val response = tokenPreferences.getToken()?.let { materiRepository.getSubMateriById(it,id) }
            viewModelScope.launch(Dispatchers.Main) {
                if (response!!.payload != null){
                    _subMenuMateriTeacherResponse.postValue(Resource.Success(response.payload))
                }else{
                    _subMenuMateriTeacherResponse.postValue(Resource.Error(response.exception,"Error"))
                }
            }
        }catch (e:Exception){
            viewModelScope.launch(Dispatchers.Main) {
                _subMenuMateriTeacherResponse.postValue(Resource.Error(e,"Error"))
            }
        }
    }
}