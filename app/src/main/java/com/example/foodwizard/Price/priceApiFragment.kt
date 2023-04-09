package com.example.foodwizard.Price/*
package com.bignerdranch.android.price

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.price.databinding.FragmentPriceApiBinding
import kotlinx.coroutines.*
import retrofit2.await
import kotlin.coroutines.CoroutineContext

class priceApiFragment : Fragment(), CoroutineScope {

    private lateinit var binding : FragmentPriceApiBinding

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create an api interface
        val apiInterface = ApiClient.retrofit.create(ApiInterface::class.java)
        // launch a coroutine to make the api call
        launch {
            try {
                val myapi = "7E7D26AB23B04E929FEEE6151E907080"
                val ASIN = "B07R4B7L41"
                val productResponse = apiInterface.getProductDetails(myapi, "product", "amazon.com", ASIN).await()
            } catch (e : Exception) {
                println("An error occurred.")
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPriceApiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

}

*/