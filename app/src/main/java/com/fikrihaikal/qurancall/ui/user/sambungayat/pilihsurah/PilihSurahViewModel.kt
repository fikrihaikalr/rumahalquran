package com.fikrihaikal.qurancall.ui.user.sambungayat.pilihsurah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.network.model.response.sambungayat.Data
import com.fikrihaikal.qurancall.network.service.sambungayat.AyatClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class PilihSurahViewModel: ViewModel() {
    private val _daftarSurat = MutableLiveData<List<Data>>()
    val daftarSurat: LiveData<List<Data>> = _daftarSurat
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadDaftarSurat() {
        _isLoading.value = true // Mulai loading
        viewModelScope.launch {
            // Contoh, anggap setiap surat tersedia dari 1 sampai 114
            val listSurat = (1..114).map { nomor ->
                AyatClient.api.getAyat(nomor).awaitResponse().body()?.data
            }.filterNotNull()
            _daftarSurat.value = listSurat
            _isLoading.value = false // Selesai loading
        }
    }
}