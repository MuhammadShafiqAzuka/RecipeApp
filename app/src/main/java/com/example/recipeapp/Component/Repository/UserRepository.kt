package com.example.recipeapp.Component.Repository

import androidx.annotation.WorkerThread
import com.example.recipeapp.Component.DAO.RecipeDAO
import com.example.recipeapp.Component.DAO.UsersDao
import com.example.recipeapp.Component.Model.Recipe
import com.example.recipeapp.Component.Model.User
import kotlinx.coroutines.flow.Flow

class UserRepository (private val usersDao: UsersDao) {

    //Repository is to store all methods connecting from DAO

    val user: Flow<MutableList<User>> = usersDao.insertUserAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User){
        usersDao.insertUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun readLoginData(name: String, password: String) :User{
        return usersDao.readLoginData(name, password)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUserDataDetails(id : Int) :User{
        return usersDao.getUserDataDetails(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        usersDao.deleteAll()
    }
}