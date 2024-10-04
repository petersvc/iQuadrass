package com.example.iquadras.restClient

import com.example.iquadras.service.BookingServiceIF
import com.example.iquadras.service.CourtServiceIF
import com.example.iquadras.service.UserServiceIF
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // private const val BASE_URL = "https://atalanta.onrender.com/api/v1/"
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    // private const val BASE_URL = "http://192.168.0.11:8080/api/v1/"

    val userService: UserServiceIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserServiceIF::class.java)
    }

    val courtService: CourtServiceIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourtServiceIF::class.java)
    }

    val bookingService: BookingServiceIF by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookingServiceIF::class.java)
    }
}