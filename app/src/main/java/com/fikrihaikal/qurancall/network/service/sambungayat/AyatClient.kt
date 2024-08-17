package com.fikrihaikal.qurancall.network.service.sambungayat



import com.fikrihaikal.qurancall.network.model.response.sambungayat.SuratSebelumnya
import com.fikrihaikal.qurancall.network.model.response.sambungayat.SuratSebelumnyaDeserializer
import com.fikrihaikal.qurancall.network.model.response.sambungayat.SuratSelanjutnya
import com.fikrihaikal.qurancall.network.model.response.sambungayat.SuratSelanjutnyaDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AyatClient {

    val gson = GsonBuilder()
        .registerTypeAdapter(SuratSelanjutnya::class.java, SuratSelanjutnyaDeserializer())
        .registerTypeAdapter(SuratSebelumnya::class.java, SuratSebelumnyaDeserializer())
        .create()

    val api: AyatService by lazy {
        Retrofit.Builder()
            .baseUrl("https://equran.id/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AyatService::class.java)
    }
}

//object AyatClient{
//        val gson = GsonBuilder()
//        .registerTypeAdapter(SurahSelanjutnya::class.java, SuratSelanjutnyaDeserializer())
//        .registerTypeAdapter(SurahSebelumnya::class.java, SuratSebelumnyaDeserializer())
//        .create()
//
//    val api: AyatService by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://rqo-production.up.railway.app/api/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(AyatService::class.java)
//    }
//}