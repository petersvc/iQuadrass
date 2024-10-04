//package com.example.iquadras.model.location
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface UserLocationDAO {
//
//    @Insert
//    suspend fun insertLocation(location: UserLocation)
//
//    @Query("SELECT * FROM user_location ORDER BY timestamp DESC")
//    fun getAllLocations(): Flow<List<UserLocation>>
//}
