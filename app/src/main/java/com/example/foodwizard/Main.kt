package com.example.foodwizard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodwizard.Fragments.detail_meal
import com.example.foodwizard.databinding.ActivityMainBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val usersViewModel: UsersViewModel by viewModels()
    public
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.content) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
        supportFragmentManager
            .setFragmentResultListener("log-out", this) { requestKey, bundle ->
              Log.d("as","asasasa")
              finish()// Do something with the result
            }

        supportFragmentManager
            .setFragmentResultListener("requestKey", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getInt("bundleKey")
                //Log.d("dadas", result.toString())
                val fragmentManager = supportFragmentManager
                val newFragment = detail_meal()
                newFragment.show(fragmentManager, "dialog")
                // Do something with the result
            }
    }
}