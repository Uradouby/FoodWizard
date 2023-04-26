package com.example.foodwizard.Price

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.foodwizard.databinding.ActivityMainBinding
import com.example.foodwizard.databinding.ActivityPriceBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Price : AppCompatActivity() {

    private lateinit var binding : ActivityPriceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val myapi = "7E7D26AB23B04E929FEEE6151E907080"
        val mytype = "product"
        val mydomain = "amazon.com"
        val myasin = "B08ZFPQGK5"

        binding.submitButton.setOnClickListener {
            Log.e("submit_button", "submit button clicked")
            val call = serviceGenerator.getPosts(myapi, mytype, mydomain, myasin)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val requestInfo = response.body()?.requestInfo
                        Log.e("Success", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Failure", t.message.toString())
                }
            })
        }
    }

//    fun showPrice() {
//        binding.submitButton.setOnClickListener{ view: View ->
//            if (binding.apple.text.equals("Apple")) {
//                binding.price.text = "5"
//            }
//        }
//    }

}