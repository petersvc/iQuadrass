//package com.example.iquadras.model
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import com.example.iquadras.model.location.UserLocation
//import com.example.iquadras.model.location.UserLocationDAO
//import com.example.iquadras.model.booking.BookingHistory
//import com.example.iquadras.model.booking.BookingHistoryDAO
//
//@Database(entities = [UserLocation::class, BookingHistory::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun userLocationDAO(): UserLocationDAO
//    abstract fun bookingHistoryDAO(): BookingHistoryDAO
//}

//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import android.content.Context
//import com.example.iquadras.model.location.UserLocation
//import com.example.iquadras.model.location.UserLocationDao
//
//@Database(entities = [UserLocation::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userLocationDao(): UserLocationDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
