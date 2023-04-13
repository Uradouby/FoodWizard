package com.example.foodwizard

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.foodwizard.databinding.ActivityMainBinding
import com.example.foodwizard.databinding.ActivityRegisterBinding

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager
            .setFragmentResultListener("MainActivity", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString("bundleKey")
                if (result != null) {
                    Log.d("MainTrans",result)
                }
                // Do something with the result
            }
        setContentView(binding.root)
    }
}