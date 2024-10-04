//package com.example.iquadras.model.booking
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface BookingHistoryDAO {
//
//    @Insert
//    suspend fun insertBooking(booking: BookingHistory)
//
//    @Query("SELECT * FROM booking_history ORDER BY bookingDate DESC")
//    fun getAllBookings(): Flow<List<BookingHistory>>
//}
