package com.fikrihaikal.qurancall.ui.alquran.viewpager.surah

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurahViewModel(private val surahRepository: SurahRepository) : ViewModel() {
    private var _listSurahResponse = MutableLiveData<Resource<SurahResponse>>(Resource.Loading())
    val listSurahResponse: LiveData<Resource<SurahResponse>> get() = _listSurahResponse

    fun getListSurah(){
        viewModelScope.launch(Dispatchers.IO) {
            _listSurahResponse.postValue(surahRepository.getListSurah())
            Log.d("surahViewmodel",surahRepository.getListSurah().payload?.data.toString())
        }
    }
}