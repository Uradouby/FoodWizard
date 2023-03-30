package com.bignerdranch.android.price

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Create a retrofit instance
object ApiClient {
    private const val BASE_URL = "https://api.rainforestapi.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
