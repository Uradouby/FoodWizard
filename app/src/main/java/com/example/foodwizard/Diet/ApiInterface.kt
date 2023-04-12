package com.example.foodwizard.Diet

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiInterface {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Query("apiKey") apiKey: String,
        @Query("diet") diet: String,
        @Query("number") number: Int,
        @Part image: MultipartBody
    ): Response<DietResponse>
}


