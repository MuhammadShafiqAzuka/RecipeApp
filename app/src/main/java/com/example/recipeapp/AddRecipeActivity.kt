package com.example.recipeapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.Recipe.Model.Recipe
import com.example.recipeapp.Recipe.ViewModel.RecipeViewModel

class AddRecipeActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val recipeName = findViewById<EditText>(R.id.recipeName)
        val recipeType = findViewById<Spinner>(R.id.recipeType)
        val recipeIngredients = findViewById<EditText>(R.id.recipeIngredients)
        val recipeSteps = findViewById<EditText>(R.id.recipeSteps)
        val recipeImage = findViewById<EditText>(R.id.recipeImage)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        addRecipe(recipeName, recipeType, recipeIngredients, recipeSteps, recipeImage, btnAdd)
    }

    private fun addRecipe(
        recipeName: EditText,
        recipeType: Spinner,
        recipeIngredients: EditText,
        recipeSteps: EditText,
        recipeImage: EditText,
        btnAdd: Button
    ) {

        val types = resources.getStringArray(R.array.Types)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, types)
        recipeType.adapter = adapter
        recipeType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                btnAdd.setOnClickListener {
                    Toast.makeText(it.context, "Added!", Toast.LENGTH_SHORT).show()
                    val recipe = Recipe(
                        types[position].toString(),
                        recipeName.text.toString(),
                        recipeIngredients.text.toString(),
                        recipeSteps.text.toString(),
                        recipeImage.text.toString())

                    recipeViewModel.insert(recipe)

                    recipeName.setText("")
                    recipeIngredients.setText("")
                    recipeSteps.setText("")
                    recipeImage.setText("")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}