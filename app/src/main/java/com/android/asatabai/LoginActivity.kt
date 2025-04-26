package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : BaseActivity() {

    private lateinit var et_name: EditText
    private lateinit var et_password: EditText
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        JeepneyRoutesData.initialize(this)
        et_name = findViewById(R.id.editTextUsername)
        et_password = findViewById(R.id.editTextPassword)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val text_regis = findViewById<TextView>(R.id.signup)

        intent?.let {
            it.getStringExtra("email")?.let { email -> et_name.setText(email) }
            it.getStringExtra("password")?.let { password -> et_password.setText(password) }
        }

        text_regis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val btn_login = findViewById<Button>(R.id.btnSignIn)
        btn_login.setOnClickListener {
            doLogIn()
        }
    }

    private fun debugLogIn(){
        startActivity(Intent(this, LandingPageActivity::class.java))
    }
    private fun doLogIn() {
        val email = et_name.text.toString()
        val password = et_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Sign in using Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    val firestore = FirebaseFirestore.getInstance()

                    if (uid != null) {
                        firestore.collection("users").document(uid).get()
                            .addOnSuccessListener { document ->
                                val username = document.getString("username") ?: "User"
                                (application as AppData).username = username
                                (application as AppData).email = email
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LandingPageActivity::class.java))
                            }
                    }
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
