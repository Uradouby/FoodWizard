package com.example.foodwizard.DB

import androidx.room.ColumnInfo
import androidx.room.Embedded
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

// dietResponse, the response you achieve via the api
@Entity(tableName = "dietResponseTable")
data class DietResponse(
    @Embedded(prefix = "nutrition_response") var nutrition_response: List<Nutrition>,
    @Embedded(prefix = "category_response") var category_response: Category
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// category
@Entity(tableName = "categoryTable")
data class Category(
    @ColumnInfo(name = "category_name") var name: String,
    @ColumnInfo(name = "category_probability") var probability: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// nutrition
@Entity(tableName = "nutritionTable")
data class Nutrition(
    @ColumnInfo(name = "nutrition_title") var nutritionTitle: String,
    @ColumnInfo(name = "nutrition_value") var nutritionValue: Double,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


