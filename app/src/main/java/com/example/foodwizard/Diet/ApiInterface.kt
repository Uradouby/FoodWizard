package com.example.foodwizard.Diet

import com.example.foodwizard.DB.DietResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("food/images/analyze")
    suspend fun uploadImage(
        @Query("apiKey") apiKey: String,
        @Query("imageUrl") imageUrl : String
    ): Response<DietResponse>

}


