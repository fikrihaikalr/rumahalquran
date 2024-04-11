package com.fikrihaikal.qurancall.ui.doa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import com.fikrihaikal.qurancall.network.model.response.doa.DoaResponse
import com.fikrihaikal.qurancall.network.model.response.guru.GuruResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class DoaViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _listDoaResponse = MutableLiveData<Resource<DoaResponse>>(Resource.Loading())
    val listDoaResponse: LiveData<Resource<DoaResponse>> get() = _listDoaResponse

    fun getListDoa() {
        viewModelScope.launch {
            _listDoaResponse.postValue( dataRepository.getListDoa())
            Log.d("doa",dataRepository.getListGuru().payload?.data.toString())
        }
    }

}

//    private val _listDoaResponse = MutableLiveData<Resource<DoaResponse>>(Resource.Loading())
//    val listDoaResponse: LiveData<Resource<DoaResponse>> get() = _listDoaResponse
//
//    fun getListDoa() {
//        viewModelScope.launch {
//            _listDoaResponse.postValue( dataRepository.getListDoa())
//            Log.d("doa",dataRepository.getListDoa().payload?.data.toString())
//        }
//    }

//    private val _doaListResponse = MutableLiveData<Resource<ListDoaResponse>>()
//    val doaListResponse: LiveData<Resource<ListDoaResponse>> get() = _doaListResponse
//
//    fun getListDoa(){
//        viewModelScope.launch {
//            val result = dataRepository.getListDoa()
//            _doaListResponse.postValue(result)
//        }
//    }

//    private var _listDoa = MutableStateFlow<PagingData<DoaItem>>(PagingData.empty())
//    val listDoa = _listDoa.asStateFlow()
//
//    fun getAllDoa() {
//        dataRepository.getDoaList().cachedIn(viewModelScope).onEach {
//            _listDoa.value = it
//        }.launchIn(viewModelScope)
//    }