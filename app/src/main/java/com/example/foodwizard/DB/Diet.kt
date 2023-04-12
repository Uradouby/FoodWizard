package com.example.foodwizard.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// diet, the image you upload
@Entity(tableName = "dietTable")

data class Diet(
    @ColumnInfo(name = "diet_title") var dietTitle: String,
    @ColumnInfo(name = "diet_image") var dietImage: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// dietResponse, the response list you achieve via the api
@Entity(tableName = "dietResponseTable")

data class DietResponse(
    @ColumnInfo(name = "diet_results") var dietResult: List<Diet>,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
