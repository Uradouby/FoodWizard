package com.example.foodwizard.Price

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
Generate Api service interface to make HTTP requests GET
 */
object ServiceGenerator {
    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES) // Set connection timeout
        .readTimeout(1, TimeUnit.MINUTES)    // Set read timeout
        .writeTimeout(1, TimeUnit.MINUTES)   // Set write timeout
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.rainforestapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>) : T {
        return retrofit.create(service)
    }

}