package com.example.foodwizard.Price

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("request_info") val requestInfo: RequestInfo,
    @SerializedName("request_metadata") val requestMetadata: RequestMetadata,
    @SerializedName("product") val product: Product
)

data class RequestInfo(
    @SerializedName("success") val success: Boolean,
    @SerializedName("credits_used") val creditsUsed: Int
)

// product url
data class RequestMetadata(
    @SerializedName("amazon_url") val amazonUrl: String
)

// product title
data class Product(
    @SerializedName("title") val title: String,
    @SerializedName("buybox_winner") val buyboxWinner: BuyboxWinner,
    @SerializedName("variants") val variants: List<Variants>
)

data class BuyboxWinner(
    @SerializedName("subscribe_and_save") val subscribeAndSave: SubscribeAndSave
)

data class SubscribeAndSave(
    @SerializedName("base_price") val basePrice: BasePrice
)

data class BasePrice(
    @SerializedName("raw") val raw: String
)

data class Variants(
    @SerializedName("main_image") val mainImage: String
)





