package com.example.recipeapp.Component.Repository

import androidx.annotation.WorkerThread
import com.example.recipeapp.Component.DAO.RecipeDAO
import com.example.recipeapp.Component.Model.Recipe
import kotlinx.coroutines.flow.Flow


class RecipeRepository (private val recipeDAO: RecipeDAO) {

    //Repository is to store all methods connecting from DAO

    val recipe: Flow<MutableList<Recipe>> = recipeDAO.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(recipe: Recipe){
       recipeDAO.insert(recipe)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(name: String){
        recipeDAO.delete(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(recipe: Recipe){
        recipeDAO.update(recipe)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun filterBy(types: String) {
        recipeDAO.filterBy(types)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        recipeDAO.deleteAll()
    }
}