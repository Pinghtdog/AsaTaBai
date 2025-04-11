package com.example.asatabai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        val text_regis = findViewById<TextView>(R.id.login)
        text_regis.setOnClickListener {
            Log.e("CSIT284", "Text is Clicked!")
            Toast.makeText(this, "I'll just stop at the log-in page, kuya.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity :: class.java)
            startActivity(intent)
        }
        val btn_login = findViewById<Button>(R.id.btnSignUp)
        val etName = findViewById<EditText>(R.id.editTextUsername)
        val etPass = findViewById<EditText>(R.id.editTextPassword)
        val etConfirm = findViewById<EditText>(R.id.editTextConfirmPassword)
        btn_login.setOnClickListener {
            val name = etName.text.toString()
            val password = etPass.text.toString()
            val confirm = etConfirm.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (name.length < 4) {
                Toast.makeText(this, "Username must be at least 4 characters long", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!name.matches(Regex("^[a-zA-Z0-9]+$"))) {
                Toast.makeText(this, "Username can only contain letters and numbers", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Password fields cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!password.matches(Regex("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$"))) {
                Toast.makeText(this, "Password must contain both letters and numbers", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password != confirm) {
                return@setOnClickListener
            }
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("name", name)
                putExtra("password", password)
            }
            startActivity(intent)
        }
    }
}