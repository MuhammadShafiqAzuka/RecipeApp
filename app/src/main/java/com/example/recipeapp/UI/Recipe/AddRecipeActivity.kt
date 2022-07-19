package com.example.recipeapp.UI.Recipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.Component.Model.Recipe
import com.example.recipeapp.Component.ViewModel.RecipeViewModel
import com.example.recipeapp.MyApplication
import com.example.recipeapp.R

class AddRecipeActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).recipeRepository)
    }

    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        val recipeName = findViewById<EditText>(R.id.recipeName)
        val recipeType = findViewById<Spinner>(R.id.recipeType)
        val recipeIngredients = findViewById<EditText>(R.id.recipeIngredients)
        val recipeSteps = findViewById<EditText>(R.id.recipeSteps)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        imageView= findViewById(R.id.imageView)

        addRecipe(recipeName, recipeType, recipeIngredients, recipeSteps, btnAdd)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun addRecipe(
        recipeName: EditText,
        recipeType: Spinner,
        recipeIngredients: EditText,
        recipeSteps: EditText,
        btnAdd: Button
    ) {

        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

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
                        imageUri.toString())

                    recipeViewModel.insert(recipe)

                    recipeName.setText("")
                    recipeIngredients.setText("")
                    recipeSteps.setText("")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}