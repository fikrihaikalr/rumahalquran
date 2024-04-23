package com.fikrihaikal.qurancall.ui.user.sambungayat.detailsambungayat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrihaikal.qurancall.data.repository.SurahRepository
import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.network.model.response.sambungayat.AyatItem
import com.fikrihaikal.qurancall.network.model.response.sambungayat.AyatResponse
import com.fikrihaikal.qurancall.network.model.response.sambungayat.Data
import com.fikrihaikal.qurancall.network.service.sambungayat.AyatClient
import com.fikrihaikal.qurancall.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class DetailSambungAyatViewModel: ViewModel() {
    private var _ayatResponse = MutableLiveData<AyatResponse>()
    val ayatResponse: LiveData<AyatResponse> = _ayatResponse
    val currentAyat = MutableLiveData<AyatItem>()
    val answerOptions = MutableLiveData<List<String>>()
    var correctAnswer: String? = null

    fun loadAyat(suratNumber:Int){
        viewModelScope.launch {
            val response = try {
                AyatClient.api.getAyat(suratNumber).awaitResponse()
            }catch (e:Exception){
                Log.d("PertanyaanViewModel","Error fetching data: ${e.message}")
                e.printStackTrace()
                return@launch
            }
            if (response.isSuccessful && response.body() != null){
                val data = response.body()!!.data
                if (data != null && data.ayat != null && data.ayat.isNotEmpty()){
                    prepareQuestionAndAnswers(data)
                }else{
                    Log.d("PertanyaanViewModel","Data ayat kosong atau null")
                }
            }else{
                Log.d("PertanyaanViewModel","Response tidak berhasil atau body kosong")
            }
        }
    }

    private fun prepareQuestionAndAnswers(data: Data) {
        val ayatList = data.ayat?.filterNotNull()
        if (!ayatList.isNullOrEmpty()) {
            // Pilih ayat secara acak sebagai pertanyaan, kecuali ayat terakhir
            val questionIndex = (0 until ayatList.size - 1).random()
            val questionAyat = ayatList[questionIndex]
            currentAyat.value = questionAyat
            // Tetapkan jawaban yang benar
            correctAnswer = ayatList.getOrNull(questionIndex + 1)?.teksArab

            val options = mutableListOf<String>()
            correctAnswer?.let { options.add(it) }

            // Tambahkan jawaban yang salah dan unik
            while (options.size < 3) {
                val randomIndex = (ayatList.indices - questionIndex - 1).random() // Hindari indeks pertanyaan dan jawaban yang benar
                val randomAyat = ayatList[randomIndex].teksArab!!
                if (randomAyat !in options) {
                    options.add(randomAyat)
                }
            }

            options.shuffle()
            answerOptions.value = options
        }else {
            // Log atau tampilkan pesan kesalahan jika tidak ada ayat
            Log.e("PertanyaanViewModel", "Tidak ada ayat yang tersedia")
        }
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        return selectedAnswer.trim() == correctAnswer?.trim()
    }
}