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
                        val url = response.body()?.requestMetadata?.amazonUrl
                        val food = response.body()?.product?.title
                        val price = response.body()?.product?.buyboxWinner?.subscribeAndSave?.basePrice?.raw
                        binding.apple.text = food
                        binding.price.text = price
                        binding.priceName.text = url
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
}