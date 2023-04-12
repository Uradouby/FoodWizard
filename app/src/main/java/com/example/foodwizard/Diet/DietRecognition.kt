package com.example.foodwizard.Diet

import com.example.foodwizard.DB.Diet
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.util.concurrent.CountDownLatch
import com.example.foodwizard.Util.Constants.DIET_URL

class DietRecognition {
    // Initialize
    private val apiKey = "your_api_key"
    private val dietImage: ByteArray? = null
    private val number = 0


    // create Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(DIET_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // create instance of API interface
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    // define method to recognize a diet from an image
    suspend fun recognizeDiet(imageBytes: ByteArray): Diet? {
        // create request body with image data
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "image.jpeg",
                imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .build()

        val countDownLatch = CountDownLatch(1)
        var diet: Diet? = null

        // make API call
        try {
            val response = apiInterface.uploadImage(apiKey, "diet", number, requestBody)
            if (response.isSuccessful) {
                diet = response.body()?.getDiet()
            } else {
                // handle unsuccessful response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            countDownLatch.countDown()
        }
        return diet
    }
}
