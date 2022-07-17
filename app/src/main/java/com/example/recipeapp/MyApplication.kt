package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.Recipe.Repository.RecipeRepository
import com.example.recipeapp.Recipe.Storage.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    //Instantiate the repository with the database
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { RecipeDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { RecipeRepository(database.RecipeDAO())}
}