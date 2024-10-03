package com.example.iquadras.model.adress

import retrofit2.http.GET
import retrofit2.http.Path

interface AdressServiceIF {

    @GET("{cep}/json/")
    suspend fun getAdress(@Path("cep") cep: String): Adress

}