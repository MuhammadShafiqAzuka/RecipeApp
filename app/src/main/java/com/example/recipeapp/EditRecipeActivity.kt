package com.example.recipeapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.example.recipeapp.Recipe.Model.Recipe
import com.example.recipeapp.Recipe.ViewModel.RecipeViewModel
import com.squareup.picasso.Picasso

class EditRecipeActivity : AppCompatActivity() {

    private val recipeViewModel : RecipeViewModel by viewModels {
        RecipeViewModel.RecipeViewModelFactory((application as MyApplication).repository)
    }

    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val recipeName = findViewById<EditText>(R.id.editrecipeName)
        val recipeType = findViewById<Spinner>(R.id.editrecipeType)
        val recipeIngredients = findViewById<EditText>(R.id.editrecipeIngredients)
        val recipeSteps = findViewById<EditText>(R.id.editrecipeSteps)
        val recipeImage = findViewById<Button>(R.id.btnAddImageEdit)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        imageView= findViewById(R.id.imageViewEdit)

        editRecipe(recipeName, recipeType, recipeIngredients, recipeSteps, recipeImage, btnEdit, btnDelete)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun editRecipe(
        recipeName: EditText,
        recipeType: Spinner,
        recipeIngredients: EditText,
        recipeSteps: EditText,
        recipeImage: Button,
        btnEdit: Button,
        btnDelete: Button
    ) {

        recipeImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        val bundle = intent.extras
        if (bundle != null) {
            val name:String = bundle.getString("name").toString()
            val ingredients:String = bundle.getString("ingredients").toString()
            val steps:String = bundle.getString("steps").toString()
            val image:String = bundle.getString("image").toString()

            recipeName.setText(name)
            recipeIngredients.setText(ingredients)
            recipeSteps.setText(steps)
            Picasso.get().load(image).into(imageView);

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