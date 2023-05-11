package com.example.foodwizard.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwizard.Adapter.recipeAdapter
import com.example.foodwizard.Adapter.shopAdapter
import com.example.foodwizard.Price.ApiResponse
import com.example.foodwizard.Price.ApiService
import com.example.foodwizard.Price.ServiceGenerator
import com.example.foodwizard.Util.MarginItemDecoration
import com.example.foodwizard.databinding.FragmentListShopBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class list_shop : Fragment() {
    private var _binding: FragmentListShopBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListShopBinding.inflate(inflater, container, false)
        binding.shopRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.shopRecyclerView.addItemDecoration(
            MarginItemDecoration(64)
        )
//        val asinList = listOf("B08ZFPQGK5", "B00OO77BL6", "B07K77SH7F", "B0829QQHCS")
        val asinList = listOf("B08ZFPQGK5", "B00OO77BL6", "B07K77SH7F", "B08D7JM1X2", "B0829QQHCS",
            "B073S4TP58", "B019HV38RO", "B079LY41VY", "B09RGMRWM7", "B0B2N5GHMG")
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        // add your own key here!
        val myapi = "7E7D26AB23B04E929FEEE6151E907080"
        val mytype = "product"
        val mydomain = "amazon.com"

        val apiResponseList = mutableListOf<Response<ApiResponse>>()

        for (myasin in asinList) {
            val call = serviceGenerator.getPosts(myapi, mytype, mydomain, myasin)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    Log.d("rainforest-response",response.toString())
                    if (response.isSuccessful) {
                        // Log.e("Success", response.body().toString())
                        apiResponseList.add(response)
                    }

                    binding.shopRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        binding.shopRecyclerView.adapter = shopAdapter(apiResponseList)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Failure", t.message.toString())
                }
            })
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
