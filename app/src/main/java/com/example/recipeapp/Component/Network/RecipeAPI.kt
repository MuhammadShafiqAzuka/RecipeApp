package com.example.recipeapp.Component.Network

import com.example.recipeapp.Component.Model.Recipe
import retrofit2.http.GET

interface RecipeAPI {
    @GET("recipes/73420/information?apiKey=b3a1fba3cb284b24a76302d8d88bc8f5&includeNutrition=true/")
    suspend fun getRecipe(): Recipe
}