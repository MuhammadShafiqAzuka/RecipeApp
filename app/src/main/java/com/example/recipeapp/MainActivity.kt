package com.example.recipeapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Component.Model.Recipe
import com.example.recipeapp.Component.Network.RecipeAPI
import com.example.recipeapp.Component.Network.RetrofitHelper
import com.example.recipeapp.Component.ViewModel.RecipeViewModel
import com.example.recipeapp.UI.Recipe.AddRecipeActivity
import com.example.recipeapp.UI.Recipe.RecipeListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).recipeRepository)
    }
    private var alreadyExecuted : Boolean = false

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!alreadyExecuted) {
            val quotesApi = RetrofitHelper.getInstance().create(RecipeAPI::class.java)
            GlobalScope.launch {
                val result = quotesApi.getRecipe()
                if (result != null)
                    Log.d("ayush: ", result.toString())

                val recipe = Recipe(
                    result.type,
                    result.name,
                    result.ingredients,
                    result.steps,
                    result.image)

                recipeViewModel.insert(recipe)
            }
            alreadyExecuted = true;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        }

        filterRecipe()

        setupAdapter()

        deleteAll()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun filterRecipe() {
        val filterby = findViewById<Spinner>(R.id.filterBy)

        val types = resources.getStringArray(R.array.Types)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, types)

        filterby.adapter = adapter
        filterby.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                recipeViewModel.filterBy(types[position].toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun setupAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val fab = findViewById<FloatingActionButton>(R.id.addRecipe)
        val adapter = RecipeListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        recipeViewModel.recipe. observe(this) { recipe ->
            recipe?.let {
                adapter.submitList(it)
                Log.d("MainActivityy", "data: "+it.toString())
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteAll() {
        val deleteAll = findViewById<Button>(R.id.deleteAllBtn)
        deleteAll.setOnClickListener {
            recipeViewModel.deleteAll()
        }
    }

    var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to LOGOUT", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}