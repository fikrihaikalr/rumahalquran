package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import android.util.Log
import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import com.fikrihaikal.qurancall.network.service.surah.SurahService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed

class SurahRepository(private val surahService: SurahService,private val context:Context){
    suspend fun getListSurah():Resource<SurahResponse> =
        proceed {
            surahService.getAllSurah()
        }
    suspend fun getDetailSurah(id:String):Resource<DetailSurahResponse> =
        proceed {
            Log.d("nomorsurahrepository",id)
            val response = surahService.getDetailSurah(id)
            Log.d("surahRepo",response.toString())
            response
        }
}