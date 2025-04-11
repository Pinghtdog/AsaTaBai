package com.example.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.asatabai.data.JeepneyRoutesData

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        JeepneyRoutesData.initialize(this)
        val text_regis = findViewById<TextView>(R.id.signup)
        val et_name = findViewById<TextView>(R.id.editTextUsername)
        val et_password = findViewById<TextView>(R.id.editTextPassword)
        intent?.let {
            it.getStringExtra("name")?.let { name->
                et_name.text = name
            }
            it.getStringExtra("password")?.let { password ->
                et_password.text = password
            }
        }
        text_regis.setOnClickListener {
            Log.e("CSIT284", "Text is Clicked!")
            Toast.makeText(this, "I'll just stop at the sign-up page, kuya.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, RegisterActivity :: class.java)
            startActivity(intent)
        }
        val btn_login = findViewById<Button>(R.id.btnSignIn)
        btn_login.setOnClickListener {
            val intent = Intent(this, LandingPageActivity :: class.java)
            startActivity(intent)
        }
    }
}