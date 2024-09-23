package com.example.iquadras.model.restClient

import com.example.iquadras.model.booking.Booking
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookingServiceIF {

    @GET("booking/{id}")
    suspend fun getBooking(@Path("id") id: Long): Booking

    @GET("booking")
    suspend fun getAllBookingss(): List<Booking>

    @POST("booking")
    suspend fun createBooking(@Body booking: Booking): String

    @DELETE("booking/{id}")
    suspend fun deleteBooking(@Path("id") id: Long): String

}
