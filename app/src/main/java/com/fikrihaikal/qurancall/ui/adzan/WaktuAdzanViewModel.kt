package com.fikrihaikal.qurancall.ui.adzan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fikrihaikal.qurancall.network.model.response.adzan.DataKotaResponse
import com.fikrihaikal.qurancall.network.model.response.adzan.KotaResponse
import com.fikrihaikal.qurancall.network.service.adzan.AdzanClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaktuAdzanViewModel : ViewModel() {
    private val _cities = MutableLiveData<List<KotaResponse>>()
    val cities: LiveData<List<KotaResponse>> get() = _cities

    fun searchCities(query: String) {
        AdzanClient.api.searchCity(query).enqueue(object : Callback<DataKotaResponse> {
            override fun onResponse(
                call: Call<DataKotaResponse>,
                response: Response<DataKotaResponse>
            ) {
                if (response.isSuccessful) {
                    _cities.value = response.body()?.data ?: emptyList()
                    Log.e("adzan viewModel","berhasil")
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<DataKotaResponse>, t: Throwable) {
                // Handle network failure
            }
        })
    }
}