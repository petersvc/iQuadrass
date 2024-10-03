package com.example.iquadras.model.location

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserLocation::class], version = 1, exportSchema = false)
abstract class UserLocationDatabase : RoomDatabase() {

    abstract fun userLocationDao(): UserLocationDAO

    companion object {
        @Volatile
        private var INSTANCE: UserLocationDatabase? = null

        fun getInstance(context: Context): UserLocationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserLocationDatabase::class.java,
                    "user_location_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
