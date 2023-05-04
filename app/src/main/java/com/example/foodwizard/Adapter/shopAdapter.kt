package com.example.foodwizard.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.Meal
import com.example.foodwizard.Price.ApiResponse
import com.example.foodwizard.Price.WebviewActivity
import com.example.foodwizard.databinding.ListItemShopBinding
import com.squareup.picasso.Picasso
import retrofit2.Response

class shopAdapter(private val apiResponseList: MutableList<Response<ApiResponse>>) :
    RecyclerView.Adapter<shopHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): shopHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemShopBinding.inflate(inflater, parent, false)
        Log.d("adapter",apiResponseList.size.toString())
        return shopHolder(binding)
    }

    override fun onBindViewHolder(holder: shopHolder, position: Int) {
        holder.bind(apiResponseList[position])
    }

    override fun getItemCount(): Int {
        return apiResponseList.size
    }
}

class shopHolder(private val binding: ListItemShopBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(apiResponse: Response<ApiResponse>) {
        val myUrl = apiResponse.body()?.requestMetadata?.amazonUrl
        binding.product.text = apiResponse.body()?.product?.title
        binding.price.text =
            apiResponse.body()?.product?.buyboxWinner?.subscribeAndSave?.basePrice?.raw
        val variants = apiResponse.body()?.product?.variants
        if (variants != null) {
            Picasso.get().load(variants[0].mainImage).resize(600, 600).into(binding.foodimg)
        }

        binding.urlButton.setOnClickListener {
//          open website using browser
//          val openURL = Intent(Intent.ACTION_VIEW)
//          openURL.data = Uri.parse(myUrl.toString())
//          binding.root.context.startActivity(openURL)
            val openURL = Intent(binding.root.context, WebviewActivity::class.java)
            openURL.putExtra("url", myUrl.toString())
            binding.root.context.startActivity(openURL)
        }
    }
}