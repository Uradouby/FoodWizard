package com.example.foodwizard.Diet

import androidx.room.TypeConverter
import com.example.foodwizard.DB.DietResponse
import com.google.gson.Gson

class DietResponseConverter {
    @TypeConverter
    fun fromDietResponse(dietResponse: DietResponse?): String? {
        if (dietResponse == null) {
            return null
        }
        val gson = Gson()
        return gson.toJson(dietResponse)
    }

    @TypeConverter
    fun toDietResponse(json: String?): DietResponse? {
        if (json == null) {
            return null
        }
        val gson = Gson()
        return gson.fromJson(json, DietResponse::class.java)
    }
}
