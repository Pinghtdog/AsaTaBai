package com.android.asatabai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.android.asatabai.utils.LocaleHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.core.content.edit

class LoginActivity : BaseActivity() {

    private lateinit var et_name: EditText
    private lateinit var et_password: EditText
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        JeepneyRoutesData.initialize(this)
        et_name = findViewById(R.id.editTextUsername)
        et_password = findViewById(R.id.editTextPassword)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            autoLogin(auth.currentUser?.uid)
            return
        }

        LocaleHelper.setLocale(this, "en")

        val preferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        preferences.edit() { remove("Locale.Helper.Selected.Language") }

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
                    // User successfully signed in with Firebase Auth,
                    // now fetch user data from Firestore and navigate
                    fetchAndNavigateToLandingPage(uid)

                } else {
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    // Optionally log the exception for debugging
                    // task.exception?.printStackTrace()
                }
            }
    }



    private fun fetchAndNavigateToLandingPage(uid: String?) {
        if (uid != null) {
            // Make sure db is initialized before using it
            db = FirebaseFirestore.getInstance() // Initialize db here if not already

            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username") ?: "User"
                        val email = document.getString("email") ?: auth.currentUser?.email
                        ?: "" // Fetch email from Firestore or Auth
                        (application as AppData).username = username
                        (application as AppData).email = email
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LandingPageActivity::class.java)
                        // Add flags to clear the back stack so user can't go back to login
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish() // Finish LoginActivity
                    } else {
                        // Handle case where user exists in Auth but not in Firestore (shouldn't happen if registration flow is correct)
                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                        // Optionally sign out the user from Auth if their data is missing in Firestore
                        auth.signOut()
                        // You might want to stay on the login screen or guide the user to re-register
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Failed to fetch user data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle the error - maybe sign out the user or stay on login
                    auth.signOut()
                    // Optionally log the exception
                    // e.printStackTrace()
                }
        } else {
            // Should not happen if auth.currentUser is checked before
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show()
            // Stay on login screen
        }
    }


        private fun autoLogin(uid: String?) {
            if (uid != null) {
                db = FirebaseFirestore.getInstance()
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val username = document.getString("username") ?: "User"
                            val email = document.getString("email") ?: auth.currentUser?.email
                            ?: "" // Fetch email from Firestore or Auth
                            (application as AppData).username = username
                            (application as AppData).email = email
                            Toast.makeText(this, "AutoLogin Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LandingPageActivity::class.java)
                            // Add flags to clear the back stack so user can't go back to login
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            finish() // Finish LoginActivity
                        } else {
                            // Handle case where user exists in Auth but not in Firestore (shouldn't happen if registration flow is correct)
                            Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                            // Optionally sign out the user from Auth if their data is missing in Firestore
                            auth.signOut()
                            // You might want to stay on the login screen or guide the user to re-register
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Failed to fetch user data: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Handle the error - maybe sign out the user or stay on login
                        auth.signOut()
                        // Optionally log the exception
                        // e.printStackTrace()
                    }
            } else {
                // Should not happen if auth.currentUser is checked before
                Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show()
                // Stay on login screen
            }
    }
}
