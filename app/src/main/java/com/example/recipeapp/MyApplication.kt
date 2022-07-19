package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.Component.Repository.RecipeRepository
import com.example.recipeapp.Component.Repository.UserRepository
import com.example.recipeapp.Component.Storage.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    //Instantiate the repository with the database
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope)}
    val recipeRepository by lazy {
        RecipeRepository(database.RecipeDAO())
    }
    val userRepository by lazy {
        UserRepository(database.UsereDAO())
    }

}