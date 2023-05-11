package com.example.foodwizard

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.text.Editable
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.foodwizard.Fragments.Record
import com.example.foodwizard.Fragments.detail_meal
import com.example.foodwizard.Fragments.list_meal
import com.example.foodwizard.databinding.ActivityMainBinding
import com.example.foodwizard.databinding.ActivityRegisterBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val usersViewModel: UsersViewModel by viewModels()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    public
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.IO)
        {
            auth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = auth.currentUser
            val userId: String = user!!.uid
            databaseReference =
                FirebaseDatabase.getInstance().getReference("Users").child(userId)

            databaseReference.child("calory").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                usersViewModel.plancalory= it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("fat").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                usersViewModel.planfat= it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("protein").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                usersViewModel.planprotein= it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }

            databaseReference.child("carb").get().addOnSuccessListener {
                Log.i("firebase read", "Got value ${it.value}")
                usersViewModel.plancarb= it.value.toString().toInt()
            }.addOnFailureListener {
                Log.e("firebase read", "Error getting data", it)
            }
        }



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