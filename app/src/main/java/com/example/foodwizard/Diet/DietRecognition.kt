package com.example.foodwizard.Diet

import android.util.Log
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.Util.Constants.DIET_URL
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DietRecognition {
    // initialize
    private val apiKey = "your_api_key"
    private val retrofit = Retrofit.Builder()
        .baseUrl(DIET_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // an instance of apiInterface
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    // recognize a diet from an uploaded image
    suspend fun recognizeDiet(imageBytes: ByteArray): Any? {
        // create request body with image data
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "image.jpeg",
                imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
            )
            .build()

        val dietResponse: Response<DietResponse> =
            apiInterface.uploadImage(apiKey, "", 0, requestBody)
        return if (dietResponse.isSuccessful) {
            dietResponse.body()
        } else {
            val errorBody = dietResponse.errorBody()?.string()
            // print the error message
            Log.e("DietRecognition", "Error: $errorBody")
            return null
        }
    }
}
