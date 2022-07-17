package com.example.recipeapp.Recipe.DAO

import androidx.room.*
import com.example.recipeapp.Recipe.Model.Recipe
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface RecipeDAO {

    //Data Access Object is the method to get data

    @Query("SELECT * FROM Recipe ORDER BY recipeType")
    fun getAll(): Flow<MutableList<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Query("DELETE FROM Recipe WHERE recipeName = :name")
    fun delete(name: String)

    @Query("SELECT * FROM Recipe WHERE recipeType IN (:types)")
    fun filterBy(types: String) : Flow<List<Recipe>>

    @Query("DELETE FROM Recipe")
    fun deleteAll()
}