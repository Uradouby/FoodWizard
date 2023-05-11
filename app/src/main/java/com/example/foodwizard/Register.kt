package com.example.foodwizard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.foodwizard.DB.User
import com.example.foodwizard.databinding.ActivityRegisterBinding
import com.example.foodwizard.viewModel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

val TAG = "Register"
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
                Toast.makeText(applicationContext, getString(R.string.register_empty_fields), Toast.LENGTH_SHORT).show()
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

                    val hashMap: HashMap<String,Any > = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("email",email)
                    hashMap.put("calory",0)
                    hashMap.put("fat",0)
                    hashMap.put("carb",0)
                    hashMap.put("protein",0)

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.register_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }else{
                    // looked at https://firebase.google.com/docs/auth/android/password-auth#sign_in_a_user_with_an_email_address_and_password
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext,
                        getString(R.string.register_fail) + (it.exception?.localizedMessage ?: ""),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


}