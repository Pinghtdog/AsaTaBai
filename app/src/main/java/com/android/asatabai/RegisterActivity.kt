package com.android.asatabai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class RegisterActivity : BaseActivity() {

    private lateinit var et_username: EditText
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_register: Button
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        et_username = findViewById(R.id.editTextUsername)
        et_email = findViewById(R.id.editTextEmail)
        et_password = findViewById(R.id.editTextPassword)
        btn_register = findViewById(R.id.btnSignUp)

        btn_register.setOnClickListener {
            val username = et_username.text.toString().trim() // Trim whitespace
            val email = et_email.text.toString().trim() // Trim whitespace
            val password = et_password.text.toString().trim() // Trim whitespace

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val capitalizedUsername = username.split(" ") // Split the username by spaces
                    .joinToString(" ") { // Join the words back with spaces
                        it.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                        }
                    }
                // Create user in Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Get user ID from Firebase Auth
                            val user = auth.currentUser

                            // Create a user data map to save to Firestore (without password)
                            val userMap = hashMapOf(
                                "username" to capitalizedUsername, // Use the capitalized username
                                "email" to email
                            )

                            // Save user data in Firestore (No password)
                            user?.let {
                                db.collection("users").document(it.uid)
                                    .set(userMap)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, LoginActivity::class.java).apply {
                                            putExtra("name", email)
                                            putExtra("password", "")  // Don't pass password
                                        })
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                        user.delete()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
