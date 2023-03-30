package com.bignerdranch.android.price

import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.coroutines.Deferred

interface ApiInterface {
    @GET("request")
    fun getProductDetails(
        @Query("api_key") apiKey: String,
        @Query("type") type: String,
        @Query("amazon_domain") amazonDomain: String,
        @Query("asin") asin: String
    ): Deferred<productResponse>
}
