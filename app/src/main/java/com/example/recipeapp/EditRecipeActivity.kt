package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.example.recipeapp.Recipe.Model.Recipe
import com.example.recipeapp.Recipe.ViewModel.RecipeViewModel

class EditRecipeActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val recipeName = findViewById<EditText>(R.id.editrecipeName)
        val recipeType = findViewById<Spinner>(R.id.editrecipeType)
        val recipeIngredients = findViewById<EditText>(R.id.editrecipeIngredients)
        val recipeSteps = findViewById<EditText>(R.id.editrecipeSteps)
        val recipeImage = findViewById<EditText>(R.id.editrecipeImage)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        editRecipe(recipeName, recipeType, recipeIngredients, recipeSteps, recipeImage, btnEdit, btnDelete)
    }

    private fun editRecipe(
        recipeName: EditText,
        recipeType: Spinner,
        recipeIngredients: EditText,
        recipeSteps: EditText,
        recipeImage: EditText,
        btnEdit: Button,
        btnDelete: Button
    ) {
        val bundle = intent.extras
        if (bundle != null) {
            val name:String = bundle.getString("name").toString()
            val ingredients:String = bundle.getString("ingredients").toString()
            val steps:String = bundle.getString("steps").toString()
            val image:String = bundle.getString("image").toString()

            recipeName.setText(name)
            recipeIngredients.setText(ingredients)
            recipeSteps.setText(steps)
            recipeImage.setText(image)

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
                    btnEdit.setOnClickListener {
                        Toast.makeText(it.context, "Edited!", Toast.LENGTH_SHORT).show()
                        val recipe = Recipe(
                            types[position].toString(),
                            recipeName.text.toString(),
                            recipeIngredients.text.toString(),
                            recipeSteps.text.toString(),
                            recipeImage.text.toString())

                        recipeViewModel.update(recipe)
                    }

                    btnDelete.setOnClickListener {
                        recipeViewModel.delete(name)
                        Toast.makeText(it.context, "Deleted!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(it.context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        else{
            recipeName.text = null
            recipeIngredients.text = null
            recipeSteps.text = null
            recipeImage.text = null
        }
    }
}