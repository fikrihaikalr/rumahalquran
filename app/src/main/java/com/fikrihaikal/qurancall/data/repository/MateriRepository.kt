package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.network.service.materi.MateriService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed

class MateriRepository(private val materiService: MateriService, private val context:Context) {

    suspend fun getListMenuMateri():Resource<MenuMateriResponse> =
        proceed {
            materiService.getAllMenuMateri()
        }
}