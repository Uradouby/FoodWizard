package com.example.foodwizard

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.example.foodwizard.DB.USER_TYPE
import com.example.foodwizard.DB.User
import com.example.foodwizard.databinding.ActivityLoginBinding
import com.example.foodwizard.viewModel.UsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    private val usersViewModel: UsersViewModel by viewModels()
    var userId : Int = 0 //user id will be used to get the user data from the database
    private lateinit var userType : USER_TYPE //user type will be used to check if the user is admin or not
    private val adminUser = User("admin",ValidationManager.encryption("1234"), userType = USER_TYPE.ADMIN) //userId will be 1
    private lateinit var sharedPreferences: SharedPreferences
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        binding.title.text= Html.fromHtml(
            "<font color=${Color.parseColor("#AEFC08")}>L</font>" +
                    "<font color=${Color.parseColor("#06F23A")}>og In</font>")

        GlobalScope.launch(Dispatchers.IO) {
            usersViewModel.addUserIfNotExist(adminUser) //add admin user automatically when the app is installed
        }
        sharedPreferences = getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE)

        calculateLastLogin() //check if the user had already signed in or not

        binding.back.setOnClickListener {
            finish();
        }
//        binding.login.setOnClickListener {
//            val intent = Intent(this, Record::class.java)
//            startActivity(intent)
//        }
        setContentView(binding.root)
    }

    //----------- check if the user had already signed in for the last hour or not ----------------
    private fun calculateLastLogin(){
        val lastLogin = sharedPreferences.getLong("LAST_LOGIN", -1)
        val lastId = sharedPreferences.getInt("USER_ID", -1)
        if(lastLogin != -1L &&  System.currentTimeMillis() - lastLogin < 3600000 ) //one hour
            goToMainActivityDirectly(lastId) //
        else
            setOnSignInUpListener()
    }

    private fun setOnSignInUpListener() {
        binding.login.setOnClickListener {
            isUserValid()  //check if the user is valid or not
        }
//        sign_up_button.setOnClickListener {
//            onSignUpClick() //go to sign up fragment
//        }
    }

    //---------------------------check if the user validation ---------------------------
    private fun isUserValid() {
        usersViewModel.usersData.observe(this) {
            if (userCheck(it)) {  //if the user is valid then go to main activity
                goToMainActivity()
                binding.account.text.clear()
                binding.password.text.clear()
            } else {
                binding.account.text.clear() //clear the edit text if the user is not valid
                binding.password.text.clear()
            }
        }
    }

    private fun userCheck(users: List<User>) : Boolean { // check if the user is valid or not
        var flag = false
        for (user in users) {
            if ((user.userName == binding.account.text.toString()) &&
                ValidationManager.comparePasswordEncrypt(user.password, binding.password.text.toString())) {
                flag = true
                userId = user.id
            }
        }
        if (!flag)
            errorMotionToast() //show error toast if the user is not valid
        else
            successMotionToast() //show success toast if the user is valid
        return flag //return true if the user is valid
    }

    private fun goToMainActivityDirectly(lastId: Int) {
        userId = lastId
        userType = if (userId == 1) USER_TYPE.ADMIN else USER_TYPE.USER
        val intent = Intent(this, Main::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }

    private fun goToMainActivity() { //go to main activity
        sharedPreferences.edit().putLong("LAST_LOGIN", System.currentTimeMillis()).apply()
        sharedPreferences.edit().putInt("USER_ID", userId).apply()
        userType = if (userId == 1) USER_TYPE.ADMIN else USER_TYPE.USER
        val intent = Intent(this, Main::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }
    //----- Display Motion Toasts successes or fail --------------------------------------
    private fun errorMotionToast(){
        MotionToast.darkColorToast(
            this,
            getString(R.string.try_again),
            getString(R.string.signIn_error),
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM or MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this,R.font.circular)
        )
    }
    private fun successMotionToast(){
        MotionToast.darkColorToast(
            this,
            getString(R.string.success),
            getString(R.string.signIn_success),
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM or MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this,R.font.circular)
        )
    }
    
}