package com.example.recipeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Recipe.Model.Recipe
import com.squareup.picasso.Picasso


class RecipeListAdapter : ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            val intent = Intent( it.context, EditRecipeActivity::class.java)
            val bundle = Bundle()
            bundle.putString("name", current.name)
            bundle.putString("type", current.type)
            bundle.putString("ingredients", current.ingredients)
            bundle.putString("steps", current.steps)
            bundle.putString("image", current.image)
            intent.putExtras(bundle)
            it.context.startActivity(intent)
        }
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.recipeListName)
        private val type: TextView = itemView.findViewById(R.id.recipeListType)
        private val ingredients: TextView = itemView.findViewById(R.id.recipeListIngredients)
        private val steps: TextView = itemView.findViewById(R.id.recipeListSteps)
        private val image: ImageView = itemView.findViewById(R.id.recipeListImage)

        fun bind(recipe: Recipe) {
            name.text = recipe.name
            type.text = recipe.type
            ingredients.text = recipe.ingredients
            steps.text = recipe.steps
            Picasso.get().load(recipe.image).into(image);
        }

        companion object {
            fun create(parent: ViewGroup): RecipeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_list, parent, false)
                return RecipeViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.name == newItem.name
        }
    }
}
