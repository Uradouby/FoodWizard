package com.example.foodwizard.Price

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("request")
    fun getPosts(
        @Query("api_key") apiKey: String,
        @Query("type") type: String,
        @Query("amazon_domain") amazonDomain: String,
        @Query("asin") asin: String) : Call<ApiResponse>
}