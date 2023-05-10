package com.example.foodwizard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.foodwizard.DB.USER_TYPE
import com.example.foodwizard.DB.User
import com.example.foodwizard.databinding.ActivityRegisterBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.Reference

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private val allUsers: MutableList<User> = mutableListOf()
    private val usersViewModel: UsersViewModel by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)

        binding.title.text = Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>"+binding.title.text[0]+"</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>"+binding.title.text.substring(1)+"</font>"
        )
        auth = FirebaseAuth.getInstance()

        binding.back.setOnClickListener {
            finish()
        }
        binding.register.setOnClickListener {
            val userName = binding.account.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordConfirm = binding.repeatpassword.text.toString()

            if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) { //check if the fields are empty
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            if (password != passwordConfirm) { //check if the password and the confirm password are the same
                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }

            OnRegisterClick(userName, email, password)
        }
        setContentView(binding.root)


    }

    private fun OnRegisterClick(userName:String,email:String,password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
//                    hashMap.put("userId",userId)

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "User created Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }


//        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) { //check if the fields are empty
//            Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            return
//        } else
//            if (password != passwordConfirm) { //check if the password and the confirm password are the same
//                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
//                return
//            }else
//                for(user in allUsers!!){
//                    if(user.userName == userName){ //check if the user name already exists
//                        Toast.makeText(applicationContext, "User with this User name already exists", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//                    else
//                        if(!ValidationManager.isValidUsername(userName)){ //check if the user name already exists
//                            Toast.makeText(applicationContext, "User name must be between 4 and 20 characters and start with a letter", Toast.LENGTH_SHORT).show()
//                            return
//                        }
//                    }
//        GlobalScope.launch (Dispatchers.IO){ usersViewModel.addUser(User(userName, ValidationManager.encryption(password),USER_TYPE.USER)) }//add the user to the database
//        Toast.makeText(applicationContext, "User created Successfully", Toast.LENGTH_SHORT).show()
    }


}