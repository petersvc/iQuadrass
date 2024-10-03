package com.example.iquadras.service

import com.example.iquadras.model.court.Court
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CourtServiceIF {

    @GET("court/{id}")
    suspend fun getCourt(@Path("id") id: Long): Court

    @GET("court")
    suspend fun getAllCourts(): List<Court>

    @POST("court")
    suspend fun login(@Body court: Court): String

    @DELETE("court/{id}")
    suspend fun deleteCourt(@Path("id") id: Long): String

}
