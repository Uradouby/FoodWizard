package com.example.foodwizard.Diet

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.Util.Constants.DIET_REC_API_KEY
import com.example.foodwizard.Util.Constants.DIET_URL
import com.example.foodwizard.viewModel.UsersViewModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

class DietRecognition(val usersViewModel: UsersViewModel) {

    // initialize
    private val apiKey = DIET_REC_API_KEY
    private val retrofit = Retrofit.Builder()
        .baseUrl(DIET_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // an instance of apiInterface
    private val apiInterface = retrofit.create(ApiInterface::class.java)

    // recognize a diet from an uploaded image
    @SuppressLint("SimpleDateFormat")
    suspend fun recognizeDiet(imageUrl : String): Diet? {
        val dietResponse: Response<DietResponse> =
            apiInterface.uploadImage(apiKey, imageUrl)
        // return dietResponse
        if (dietResponse.isSuccessful) {
            val dietResponseData: DietResponse? = dietResponse.body()
            if (dietResponseData != null) {
                val diet = Diet(
                    dietTitle = dietResponseData?.category?.name?:"No Name",
                    dietImage = imageUrl,
                    dietResponse = dietResponseData,
                    date = SimpleDateFormat("MM/dd/yyyy").format(Date()),
                    userId = 123 // TODO : swap to current userId
                )
                Log.d("DietRecognition", "Insert diet")
                usersViewModel.insertDiet(diet)
                return diet
            }
            return null
        } else {
            val errorBody = dietResponse.errorBody()?.string()
            // print the error message
            Log.e("DietRecognition", "Error: $errorBody")
            return null
        }
    }
}
