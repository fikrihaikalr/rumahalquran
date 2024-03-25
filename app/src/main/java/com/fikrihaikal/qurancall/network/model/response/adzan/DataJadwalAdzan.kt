package com.fikrihaikal.qurancall.network.model.response.adzan

data class DataJadwalAdzan(
    val id: Int,
    val lokasi: String,
    val daerah: String,
    val jadwal: JadwalAdzanResponse
)
