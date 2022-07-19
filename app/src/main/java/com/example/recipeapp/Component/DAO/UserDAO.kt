package com.example.recipeapp.Component.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.Component.Model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    //for list of users insert
    @Query("SELECT * FROM User ORDER BY userId")
    fun insertUserAll() : Flow<MutableList<User>>

    //for single user insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: User): Long

    //checking user exist or not in our db
    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password")
    fun readLoginData(username: String, password: String): User

    //getting user data details
    @Query("select * from User where userId Like :id")
    fun getUserDataDetails(id: Int): User

    //deleting all user from db
    @Query("DELETE FROM User")
    fun deleteAll()
}