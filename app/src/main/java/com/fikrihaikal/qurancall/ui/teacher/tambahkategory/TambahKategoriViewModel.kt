package com.fikrihaikal.qurancall.ui.teacher.tambahkategory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.MateriRepository
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import com.fikrihaikal.qurancall.utils.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TambahKategoriViewModel(private val materiRepository: MateriRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private val _addCategory = MutableLiveData<Resource<TambahKategoriResponse>>()
    val addCategory : LiveData<Resource<TambahKategoriResponse>> get() = _addCategory

    fun category(tambahKategoriBody: TambahKategoriBody){
        _addCategory.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = materiRepository.postCategory(tokenPreferences.getToken().orEmpty(),tambahKategoriBody)
            viewModelScope.launch(Dispatchers.Main) {
                _addCategory.postValue(response)
            }
        }
    }
}