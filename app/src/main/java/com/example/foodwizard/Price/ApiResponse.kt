package com.example.foodwizard.Price

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("request_info") val requestInfo: RequestInfo,
//    @SerializedName("request_metadata") val requestMetadata: RequestMetadata,
//    @SerializedName("product") val product: Product
)

data class RequestInfo(
    @SerializedName("success") val success: Boolean,
    @SerializedName("credits_used") val creditsUsed: Int
)

// product url
//data class RequestMetadata(
//    @SerializedName("amazon_url") val amazonUrl: String
//)

// product title
//data class Product(
//    @SerializedName("product") val product: String
//)





