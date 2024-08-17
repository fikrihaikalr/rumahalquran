package com.fikrihaikal.qurancall.data.repository

import android.content.Context
import android.util.Log
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriBody
import com.fikrihaikal.qurancall.network.model.response.addmateri.AddMateriResponse
import com.fikrihaikal.qurancall.network.model.response.deletemateri.DeleteMateriResponse
import com.fikrihaikal.qurancall.network.model.response.deletsubmateri.DeleteSubMateriResponse
import com.fikrihaikal.qurancall.network.model.response.detailcontentmateri.DetailContentMateriResponse
import com.fikrihaikal.qurancall.network.model.response.detailsurah.DetailSurahResponse
import com.fikrihaikal.qurancall.network.model.response.login.LoginBody
import com.fikrihaikal.qurancall.network.model.response.login.LoginResponse
import com.fikrihaikal.qurancall.network.model.response.menumateri.MenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.submenumateri.SubMenuMateriResponse
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriBody
import com.fikrihaikal.qurancall.network.model.response.tambahkategori.TambahKategoriResponse
import com.fikrihaikal.qurancall.network.service.materi.MateriService
import com.fikrihaikal.qurancall.utils.Resource
import com.fikrihaikal.qurancall.utils.proceed

class MateriRepository(private val materiService: MateriService, private val context:Context) {

    suspend fun getListMenuMateri(token:String):Resource<MenuMateriResponse> =
        proceed {
            materiService.getAllMenuMateri(token)
        }

    suspend fun getSubMateriById(token:String,id:String):Resource<SubMenuMateriResponse> =
        proceed {
            Log.d("nomorSubMateri",id)
            val response = materiService.getSubMateriById(token,id)
            Log.d("subMateriRepo",response.toString())
            response
        }

    suspend fun getContentMateriById(token: String,id:String):Resource<DetailContentMateriResponse> =
        proceed {
            Log.d("nomorDetailContent",id)
            val response = materiService.getContentMateriById(token,id)
            Log.d("detailContentMateriRepo",response.toString())
            response
        }

    suspend fun postCategory(token:String,tambahKategoriBody: TambahKategoriBody): Resource<TambahKategoriResponse> =
        proceed {
            materiService.postCategory(token,tambahKategoriBody)
        }

    suspend fun deleteMateri(token: String,id:String):Resource<DeleteMateriResponse> =
        proceed {
            materiService.deleteMateri(token,id)
        }

    suspend fun deleteSubMateri(token: String,id:String):Resource<DeleteSubMateriResponse> =
        proceed {
            materiService.deleteSubMateri(token,id)
        }
    suspend fun addMateri(token: String,addMateriBody: AddMateriBody):Resource<AddMateriResponse> =
        proceed {
            materiService.addMateri(token,addMateriBody)
        }
}