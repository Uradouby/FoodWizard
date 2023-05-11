package com.example.foodwizard.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.Util.RecipeUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsersViewModel(val app: Application): AndroidViewModel(app) {

    private val repository = Repository.getInstance(app.applicationContext)
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
//    val usersData: LiveData<List<User>> = repository.getAllUsers()

    var plancalory=0
    var plancarb=0
    var planfat=0
    var planprotein=0

    var meals=RecipeUtils(this).getRecommendDiet()

    private var sign=true
    private var planSign = true

    fun insertDiet(diet: Diet){
        repository.insertDiet(diet)
    }

    fun getTodayMeal(userId: String, today: String) : List<Diet>{
        return repository.getTodayMeal(userId,today)
    }

    fun getAllMeal() : MutableList<Diet>{
        return repository.getAllMeal()
    }
    fun updateSign()
    {
        sign=true
        planSign = true
    }
    fun refreshRecommend()
    {
        if (sign)
        {
            Log.d("dadada","update!!!")
            meals = RecipeUtils(this).getRecommendDiet()
            Log.d("daa",meals.toString())
            sign=false
        }
    }

    fun refreshPlan(){
        if (planSign)
        {
            GlobalScope.launch(Dispatchers.IO)
            {
                auth = FirebaseAuth.getInstance()
                val user: FirebaseUser? = auth.currentUser
                val userId: String = user!!.uid
                databaseReference =
                    FirebaseDatabase.getInstance().getReference("Users").child(userId)

                databaseReference.child("calory").get().addOnSuccessListener {
                    Log.i("firebase read", "Got value ${it.value}")
                    plancalory= it.value.toString().toInt()
                }.addOnFailureListener {
                    Log.e("firebase read", "Error getting data", it)
                }

                databaseReference.child("fat").get().addOnSuccessListener {
                    Log.i("firebase read", "Got value ${it.value}")
                    planfat= it.value.toString().toInt()
                }.addOnFailureListener {
                    Log.e("firebase read", "Error getting data", it)
                }

                databaseReference.child("protein").get().addOnSuccessListener {
                    Log.i("firebase read", "Got value ${it.value}")
                    planprotein= it.value.toString().toInt()
                }.addOnFailureListener {
                    Log.e("firebase read", "Error getting data", it)
                }

                databaseReference.child("carb").get().addOnSuccessListener {
                    Log.i("firebase read", "Got value ${it.value}")
                    plancarb= it.value.toString().toInt()
                }.addOnFailureListener {
                    Log.e("firebase read", "Error getting data", it)
                }
                planSign=false
            }
            while(planSign){

            }
        }
    }
//    fun addUser(user: User) {
//        repository.addUser(user)
//    }
//
//    fun getUserByUsername(userName: String): User {
//        return repository.getUserByUsername(userName)
//    }
//
//    fun getUserById(userId: Int): User {
//        return repository.getUserById(userId)
//    }
//
//    fun updateUserName(userId: Int, userName: String) {
//        GlobalScope.launch(Dispatchers.IO) {
//            repository.updateUserName(userId, userName)
//        }
//    }
//
//    fun updateUserPassword(userId: Int, password: String) {
//        GlobalScope.launch(Dispatchers.IO) {
//            repository.updateUserPassword(userId, password)
//        }
//    }
//
//    fun addUserIfNotExist(adminUser: User) {
//        GlobalScope.launch(Dispatchers.IO) {
//            if (repository.getUserById(adminUser.id) == null) {
//                repository.addUser(adminUser)
//            }
//        }
//    }
}