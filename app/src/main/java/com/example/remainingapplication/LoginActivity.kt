package com.example.remainingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.remainingapplication.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        databaseHelper = DatabaseHelper(this)

        binding.loginBtn.setOnClickListener(){
            val signupUsername = binding.fullNameEt.text.toString()
            val signupPassword = binding.passwordEt.text.toString()

            loginDatabase(signupUsername, signupPassword)
        }

        binding.register.setOnClickListener(){
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun loginDatabase(email: String, password: String){
        val Username = findViewById<TextInputEditText>(R.id.fullNameEt).text.toString().trim()
        val Password = findViewById<TextInputEditText>(R.id.passwordEt).text.toString().trim()
        val userExists = databaseHelper.readUser(email, password)

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if (Username.isEmpty() || Password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (userExists){
                    Toast.makeText(this, "Login Successful :))", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed :((", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}