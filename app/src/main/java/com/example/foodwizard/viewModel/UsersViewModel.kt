package com.example.foodwizard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.foodwizard.DB.Diet
import com.example.foodwizard.DB.Repository
import com.example.foodwizard.DB.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class UsersViewModel(val app: Application): AndroidViewModel(app) {

    private val repository = Repository.getInstance(app.applicationContext)
//    val usersData: LiveData<List<User>> = repository.getAllUsers()

    fun insertDiet(diet: Diet){
        repository.insertDiet(diet)
    }

    fun getTodayMeal(userId: String, today: String) : List<Diet>{
        return repository.getTodayMeal(userId,today)
    }

    fun getAllMeal() : MutableList<Diet>{
        return repository.getAllMeal()
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