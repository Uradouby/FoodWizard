package com.example.foodwizard.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {
    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("Select * from usersTable")
    fun getAllUsers(): LiveData<List<User>>

    @Query("Select * from usersTable where user_name = :username and password = :password")
    fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Query("Select * from usersTable where id = :userId")
    fun getUserById(userId: Int): User

    @Query("Select * from usersTable where user_name = :userName")
    fun getUserByUsername(userName: String): User

    @Query("Update usersTable set user_name = :userName where id = :userId")
    fun updateUserName(userId: Int, userName: String)

    @Query("Update usersTable set password = :password where id = :userId")
    fun updateUserPassword(userId: Int, password: String)

    @Query("Select user_type from usersTable where id = :userId")
    fun getUserType(userId: Int): USER_TYPE
}
