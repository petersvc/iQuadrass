package com.example.iquadras.model.restClient

import com.example.iquadras.model.adress.AdressServiceIF
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://viacep.com.br/ws/"

    val adressService: AdressServiceIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdressServiceIF::class.java)
    }
}