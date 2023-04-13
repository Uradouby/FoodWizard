package com.example.foodwizard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.foodwizard.databinding.ActivityLoginBinding
import java.util.zip.Inflater

import androidx.activity.viewModels
import com.example.foodwizard.viewModel.UsersViewModel
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.example.foodwizard.DB.User

class login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    private val usersViewModel: UsersViewModel by viewModels()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>L</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>og In</font>")
        binding.back.setOnClickListener {
            finish();
        }
        binding.login.setOnClickListener {
            var loginBool = OnLoginClick()
            if (loginBool) {
                val intent = Intent(this, Record::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "Login Error!", Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
    }

    private fun OnLoginClick():Boolean {
        val userName = binding.account.text.toString()
        val password = binding.password.text.toString()

        if (userName.isEmpty() || password.isEmpty()) { // check if the fields are empty
            Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        var user  = usersViewModel.getUserByUsername(userName)
        if (user.password==password) {
            return true
        }

        return false
    }
}