package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Recipe.Model.Recipe
import com.example.recipeapp.Recipe.Network.RecipeAPI
import com.example.recipeapp.Recipe.Network.RetrofitHelper
import com.example.recipeapp.Recipe.ViewModel.RecipeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).repository)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterRecipe()

        setupAdapter()

        deleteAll()
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

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupAdapter() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val fab = findViewById<FloatingActionButton>(R.id.addRecipe)
        val adapter = RecipeListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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

        recipeViewModel.recipe.observe(this) { recipe ->
            recipe?.let {
                adapter.submitList(it)
            }
        }

        fab.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun deleteAll() {
        val deleteAll = findViewById<Button>(R.id.deleteAllBtn)
        deleteAll.setOnClickListener {
            recipeViewModel.deleteAll()
        }
    }
}