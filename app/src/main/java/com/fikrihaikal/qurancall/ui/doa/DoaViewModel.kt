package com.fikrihaikal.qurancall.ui.doa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fikrihaikal.qurancall.data.repository.DataRepository
import com.fikrihaikal.qurancall.network.model.response.doa.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class DoaViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private var _listDoa = MutableStateFlow<PagingData<DataItem>>(PagingData.empty())
    val listDoa = _listDoa.asStateFlow()

    fun getAllDoa(){
        dataRepository.getDoa().cachedIn(viewModelScope).onEach {
            _listDoa.value = it
        }.launchIn(viewModelScope)
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