package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import com.fikrihaikal.qurancall.network.model.response.surah.SurahResponse
import com.fikrihaikal.qurancall.network.service.surah.SurahService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed

class SurahRepository(private val surahService: SurahService,private val context:Context){

    suspend fun getAllSurah():Resource<SurahResponse> =
    proceed {
        surahService.getAllSurah()
    }
}