package com.example.recipeapp.UI.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.example.recipeapp.Component.ViewModel.UserViewModel
import com.example.recipeapp.MainActivity
import com.example.recipeapp.MyApplication
import com.example.recipeapp.R

class LoginActivity : AppCompatActivity() {

    private val userViewModel : UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as MyApplication).userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.login_username)
        val password = findViewById<EditText>(R.id.login_password)
        val login_btn = findViewById<Button>(R.id.login_btn)
        val login_btn_register = findViewById<Button>(R.id.login_btn_register)

        userViewModel.user.observe(this){
            register(login_btn_register)
            login(username, password, login_btn, it[0].username, it[0].password)
        }
    }

    private fun login(
        username: EditText,
        password: EditText,
        login_btn: Button,
        username1: String,
        password1: String
    ) {
        login_btn.setOnClickListener {

            if (username.text.toString() == username1 && password.text.toString() == password1){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                username.error = "Username not exist"
                password.error = "Password not match"
            }
            //Log.d("LoginActivityy", "status: "+username1"
        }
    }

    private fun register(login_btn_register: Button) {
        login_btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}