package com.example.foodwizard.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class ), version = 1 )

abstract class AppDataBase: RoomDatabase() {

    abstract fun getUserDao(): UsersDao

    companion object {
        fun getAppDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context!!.applicationContext,
                AppDataBase::class.java,
                "app_database"
            ).build()

        }
    }
}