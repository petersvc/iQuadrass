package com.example.iquadras.service

import com.example.iquadras.model.booking.Booking
import com.example.iquadras.model.booking.DTOBooking
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingServiceIF {

    @GET("booking/{id}")
    suspend fun getBooking(@Path("id") id: Long): Booking

    @GET("booking")
    suspend fun getAllBookings(@Query("userId") userId: Long?): List<Booking>

    @POST("booking")
    suspend fun createBooking(@Body booking: DTOBooking): Booking

    @DELETE("booking/{id}")
    suspend fun deleteBooking(@Path("id") id: Long): Response<Void>

}
