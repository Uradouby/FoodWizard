package com.example.foodwizard.Diet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwizard.DB.DietResponse
import com.example.foodwizard.databinding.ActivityDietBinding

class DietAdapter : ListAdapter<DietResponse, DietAdapter.DietViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        return DietViewHolder(ActivityDietBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DietViewHolder(private val binding: ActivityDietBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dietResponse: DietResponse) {
            // get the first diet object in the list
            val diet = dietResponse.dietResult.firstOrNull()
            // set the result of the diet
            binding.dietResult.text = diet?.dietTitle
        }
    }

    object DIFF_CALLBACK : DiffUtil.ItemCallback<DietResponse>() {
        // whether the items are the same
        override fun areItemsTheSame(oldItem: DietResponse, newItem: DietResponse) =
            oldItem.id == newItem.id
        // whether contents are the same
        override fun areContentsTheSame(oldItem: DietResponse, newItem: DietResponse) =
            oldItem == newItem
    }
}
