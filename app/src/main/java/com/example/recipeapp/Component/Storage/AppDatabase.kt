package com.example.recipeapp.Component.Storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipeapp.Component.DAO.RecipeDAO
import com.example.recipeapp.Component.DAO.UsersDao
import com.example.recipeapp.Component.Model.Recipe
import com.example.recipeapp.Component.Model.User
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Recipe::class, User::class], version = 7
    , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //Database created for the core

    abstract fun RecipeDAO(): RecipeDAO
    abstract fun UsereDAO(): UsersDao

    companion object DatabaseBuilder {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,

                    "recipe_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // insert the data on the IO Thread
//                            Thread {
//                                getDatabase(context, scope).RecipeDAO().insert(PREPOPULATE_DATA)
//                            }.start()
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance

            }
        }
        //val PREPOPULATE_DATA = Recipe("Breakfast", "Egg Sandwich", "1. Two eggs. 2. Two breads.", "1. Smash eggs. 2. Put eggs in bread. 3. Eat","https://spoonacular.com/recipeImages/632660-312x231.jpg")

    }
}