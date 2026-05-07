package com.example.remainingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.remainingapplication.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.registerBtn.setOnClickListener(){
            val signupUsername = binding.fullNameEt.text.toString()
            val signupEmail = binding.emailEt.text.toString()
            val signupPassword = binding.passwordEt.text.toString()
            val signupConfirmPassword = binding.cPasswordEt.text.toString()

            signupDatabase(signupUsername, signupPassword)
        }

        binding.login.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signupDatabase(username: String, password: String){
        val Username = findViewById<TextInputEditText>(R.id.fullNameEt).text.toString().trim()
        val Email = findViewById<TextInputEditText>(R.id.emailEt).text.toString().trim()
        val Password = findViewById<TextInputEditText>(R.id.passwordEt).text.toString().trim()
        val ConfirmPassword = findViewById<TextInputEditText>(R.id.cPasswordEt).text.toString().trim()

        val insertRowId = databaseHelper.insertUser(username, password)

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if (Username.isEmpty() || Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (insertRowId != -1L){
                    Toast.makeText(this, "Signup Successful :))", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Signup failed :((", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}