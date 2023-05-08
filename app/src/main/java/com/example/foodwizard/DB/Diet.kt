package com.example.foodwizard.DB

import androidx.room.*
import java.io.Serializable
import java.util.*


// diet, the image you upload
@Entity(tableName = "dietsTable")
data class Diet(
    @ColumnInfo(name = "diet_title") var dietTitle: String,
    @ColumnInfo(name = "diet_image_url") var dietImage: String,
    @ColumnInfo(name = "dietResponse") var dietResponse: DietResponse?,
//    @Embedded var dietResponse: DietResponse?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "userId") var userId: String,

): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// dietResponse, the response you achieve via the api
@Entity(tableName = "dietResponseTable")
data class DietResponse(
    @Embedded(prefix = "nutrition") var nutrition: Nutrition,
    @Embedded(prefix = "category") var category: Category,
    @Embedded(prefix = "recipes") var recipes: List<Recipe>,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// category
@Entity(tableName = "categoryTable")
data class Category(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "probability") var probability: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// nutrition
@Entity(tableName = "nutritionTable")
data class Nutrition(
//    @ColumnInfo(name = "recipesUsed") var recipesUsed: Int,
    @ColumnInfo(name = "calories") var calories: Nutrient,
    @ColumnInfo(name = "fat") var fat: Nutrient,
    @ColumnInfo(name = "protein") var protein: Nutrient,
    @ColumnInfo(name = "carbs") var carbs: Nutrient,
//    @Embedded(prefix = "calories") var calories: Nutrient,
//    @Embedded(prefix = "fat") var fat: Nutrient,
//    @Embedded(prefix = "protein") var protein: Nutrient,
//    @Embedded(prefix = "carbs") var carbs: Nutrient,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "nutrientTable")
data class Nutrient(
    @ColumnInfo(name = "value") var value: Int,
    @ColumnInfo(name = "unit") var unit: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "recipeTable")
data class Recipe(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "url") var url: String,
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

//class DateConverters {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time?.toLong()
//    }
//}


