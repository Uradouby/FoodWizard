package com.example.foodwizard.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodwizard.Diet.DietResponseConverter

@Database(entities = [User::class,Diet::class], version = 6, exportSchema = false)
@TypeConverters(DietResponseConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getUserDao(): UsersDao
    abstract fun getMealDao(): MealDao
    companion object {
        fun getAppDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_database"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}