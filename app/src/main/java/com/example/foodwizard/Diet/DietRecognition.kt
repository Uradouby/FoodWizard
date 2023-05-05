package com.example.foodwizard.Diet

import android.annotation.SuppressLint
import android.text.Editable
import android.util.Log
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.R
import com.example.foodwizard.Util.Constants.DIET_REC_API_KEY
import com.example.foodwizard.Util.Constants.DIET_URL
import com.example.foodwizard.login
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

class DietRecognition(val usersViewModel: UsersViewModel) {


    private lateinit var auth: FirebaseAuth

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
    suspend fun recognizeDiet(imageUrl: String, description: String): Diet? {
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        var currentUserId = user!!.uid
        val dietResponse: Response<DietResponse> =
            apiInterface.uploadImage(apiKey, imageUrl)
        // return dietResponse
        if (dietResponse.isSuccessful) {
            val dietResponseData: DietResponse? = dietResponse.body()
            if (dietResponseData != null) {
                val diet = currentUserId?.let {
                    Diet(
                        dietTitle = dietResponseData?.category?.name?:"No Name",
                        dietImage = imageUrl,
                        dietResponse = dietResponseData,
                        date = SimpleDateFormat("MM/dd/yyyy").format(Date()),
                        description = description,
                        userId = it
                    )
                }
                Log.d("DietRecognition", "Insert diet")
                if (diet != null) {
                    usersViewModel.insertDiet(diet)
                }
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
