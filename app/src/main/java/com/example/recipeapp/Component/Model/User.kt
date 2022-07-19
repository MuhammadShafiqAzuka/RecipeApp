package com.example.recipeapp.Component.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    //Model data are attributes of the data
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") var userId: Int = 1
}