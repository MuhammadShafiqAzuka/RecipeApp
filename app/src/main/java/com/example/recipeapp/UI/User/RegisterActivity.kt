package com.example.recipeapp.UI.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.recipeapp.Component.Model.User
import com.example.recipeapp.Component.ViewModel.UserViewModel
import com.example.recipeapp.MyApplication
import com.example.recipeapp.R

class RegisterActivity : AppCompatActivity() {

    private val userViewModel : UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as MyApplication).userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.register_username)
        val password = findViewById<EditText>(R.id.register_password)
        val register_btn = findViewById<Button>(R.id.register_btn)

        register(username, password, register_btn)
    }

    private fun register(
        username: EditText,
        password: EditText,
        register_btn: Button
    ) {
        register_btn.setOnClickListener {

        if (username.text.isEmpty() && password.text.isEmpty()){
            username.error = "Not empty"
            password.error = "Not empty"

        }else{
                val user = User(
                    username.text.toString(),
                    password.text.toString())

                userViewModel.insert(user)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}