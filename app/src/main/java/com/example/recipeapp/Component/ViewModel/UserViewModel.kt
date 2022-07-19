package com.example.recipeapp.Component.ViewModel

import androidx.lifecycle.*
import com.example.recipeapp.Component.Model.User
import com.example.recipeapp.Component.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    //View Model is to access methods for UI

    val user : LiveData<MutableList<User>> = userRepository.user.asLiveData()

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO){
        userRepository.insert(user)
    }

    fun readLoginData(name: String, password: String) = viewModelScope.launch(Dispatchers.IO){
        userRepository.readLoginData(name, password)
    }

    fun getUserDataDetails(id : Int) = viewModelScope.launch(Dispatchers.IO){
        userRepository.getUserDataDetails(id)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        userRepository.deleteAll()
    }

    class UserViewModelFactory(private val userRepository: UserRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }
}