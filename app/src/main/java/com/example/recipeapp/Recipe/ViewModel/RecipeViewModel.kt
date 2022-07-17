package com.example.recipeapp.Recipe.ViewModel

import androidx.lifecycle.*
import com.example.recipeapp.Recipe.Model.Recipe
import com.example.recipeapp.Recipe.Repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    //View Model is to access methods for UI

    val recipe : LiveData<MutableList<Recipe>> = recipeRepository.recipe.asLiveData()

    fun insert(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO){
        recipeRepository.insert(recipe)
    }

    fun delete(name: String) = viewModelScope.launch(Dispatchers.IO){
        recipeRepository.delete(name)
    }

    fun update(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO){
        recipeRepository.update(recipe)
    }

    fun filterBy(types: String) = viewModelScope.launch(Dispatchers.IO){
        recipeRepository.filterBy(types)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        recipeRepository.deleteAll()
    }

    class RecipeViewModelFactory(private val recipeRepository: RecipeRepository)
        :ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return RecipeViewModel(recipeRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}