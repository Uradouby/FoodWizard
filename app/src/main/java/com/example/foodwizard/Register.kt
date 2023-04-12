package com.example.foodwizard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.foodwizard.DB.User
import com.example.foodwizard.databinding.ActivityRegisterBinding
import com.example.foodwizard.viewModel.UsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {
    private val allUsers: List<User> ?=null
    private val usersViewModel: UsersViewModel by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>R</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>egister</font>")
        binding.back.setOnClickListener {
            finish();
        }
        binding.register.setOnClickListener {
            OnRegisterClick()
        }
        setContentView(binding.root)

    }

    private fun OnRegisterClick(){
        val userName = binding.account.text.toString()
        val password = binding.password.text.toString()
        val passwordConfirm = binding.repeatpassword.text.toString()

        if (userName.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) { //check if the fields are empty
            Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        } else
            if (password != passwordConfirm) { //check if the password and the confirm password are the same
                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return
            }else
                for(user in allUsers!!){
                    if(user.userName == userName){ //check if the user name already exists
                        Toast.makeText(applicationContext, "User with this User name already exists", Toast.LENGTH_SHORT).show()
                        return
                    }
                    else
                        if(!ValidationManager.isValidUsername(userName)){ //check if the user name already exists
                            Toast.makeText(applicationContext, "User name must be between 4 and 20 characters and start with a letter", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
        GlobalScope.launch (Dispatchers.IO){ usersViewModel.addUser(User(userName, ValidationManager.encryption(password))) }//add the user to the database
        Toast.makeText(applicationContext, "User created Successfully", Toast.LENGTH_SHORT).show()
    }


}