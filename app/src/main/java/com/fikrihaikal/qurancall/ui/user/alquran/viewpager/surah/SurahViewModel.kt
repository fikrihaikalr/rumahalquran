package com.fikrihaikal.qurancall.ui.user.alquran.viewpager.surah

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.model.response.surah.DataItem
import com.fikrihaikal.qurancall.network.model.response.surah.MessageResponse
import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurahViewModel(private val surahRepository: SurahRepository,private val tokenPreferences: TokenPreferences) : ViewModel() {
    private var _listSurahResponse = MutableLiveData<Resource<SurahResponse>>(Resource.Loading())
    val listSurahResponse: LiveData<Resource<SurahResponse>> get() = _listSurahResponse

    fun getListSurah(){
        viewModelScope.launch(Dispatchers.IO) {
            _listSurahResponse.postValue(surahRepository.getListSurah(tokenPreferences.getToken().orEmpty()))
            Log.d("surahViewmodel",surahRepository.getListSurah(tokenPreferences.getToken().orEmpty()).payload?.data.toString())
        }
    }
}