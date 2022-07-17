package com.example.recipeapp.Recipe.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Recipe(
    //Model data are attributes of the data
    @ColumnInfo(name = "recipeType") @field : SerializedName("creditsText") val type: String,
    @ColumnInfo(name = "recipeName") @field : SerializedName("title") val name: String,
    @ColumnInfo(name = "recipeIngredients") @field : SerializedName("sourceName") val ingredients: String,
    @ColumnInfo(name = "recipeSteps") @field : SerializedName("license") val steps: String,
    @ColumnInfo(name = "recipeImage") @field : SerializedName("image") val image: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId") var recipeId: Int = 0
}