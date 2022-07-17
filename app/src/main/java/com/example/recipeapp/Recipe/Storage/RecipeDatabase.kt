package com.example.recipeapp.Recipe.Storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.Recipe.DAO.RecipeDAO
import com.example.recipeapp.Recipe.Model.Recipe
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Recipe::class], version = 4
    , exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    //Database created for the core

    abstract fun RecipeDAO(): RecipeDAO

    companion object DatabaseBuilder {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RecipeDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,

                    "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}