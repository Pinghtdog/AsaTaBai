package com.android.asatabai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.asatabai.data.AppData
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import com.android.asatabai.utils.LocaleHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class LoginActivity : BaseActivity() {

    // Make lateinit vars nullable initially or initialize later
    private lateinit var et_name: EditText
    private lateinit var et_password: EditText
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    // Flag to control splash screen duration during auto-login check
    private var isAutoLoginCheckComplete = true // Start as true (don't wait by default)

    override fun onCreate(savedInstanceState: Bundle?) {
        // --- Splash Screen Setup ---
        // 1. Install the splash screen *first*
        val splashScreen = installSplashScreen()

        // 2. Call super.onCreate() *after* installSplashScreen()
        super.onCreate(savedInstanceState)

        // 3. Keep the splash screen visible *only* if auto-login check is running
        splashScreen.setKeepOnScreenCondition { !isAutoLoginCheckComplete }

        // --- Start Jeepney Routes Initialization in the background ---
        CoroutineScope(Dispatchers.IO).launch {
            JeepneyRoutesData.initialize(applicationContext) // Initialize in the background
            withContext(Dispatchers.Main) {
                // Update UI or flag on completion if necessary
                isAutoLoginCheckComplete = true
            }
        }

        // Initialize Firebase Auth immediately
        auth = FirebaseAuth.getInstance()

        // Check if user is already logged in (auto-login scenario)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Set flag to false: We need to wait for Firestore
            isAutoLoginCheckComplete = false
            // Initialize Firestore *before* calling autoLogin
            db = FirebaseFirestore.getInstance()
            autoLogin(currentUser.uid)
            // Return here: Don't set content view or initialize UI for login page
            // as autoLogin will navigate away or handle failure.
            return
        }

        // --- User is NOT logged in, proceed to show Login Page ---
        // Set the content view for the login page
        setContentView(R.layout.loginpage)

        // Initialize Firestore and other components needed for the login page
        db = FirebaseFirestore.getInstance()

        et_name = findViewById(R.id.editTextUsername)
        et_password = findViewById(R.id.editTextPassword)

        // Initialize Locale and Preferences
        LocaleHelper.setLocale(this, "en") // Consider if this needs to run every time
        val preferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
        preferences.edit { remove("Locale.Helper.Selected.Language") } // Consider the logic here

        // Setup UI elements and listeners
        val text_regis = findViewById<TextView>(R.id.signup)
        val btn_login = findViewById<Button>(R.id.btnSignIn)

        intent?.let {
            it.getStringExtra("email")?.let { email -> et_name.setText(email) }
            it.getStringExtra("password")?.let { password -> et_password.setText(password) }
        }

        text_regis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            doLogIn()
        }
    }

    // No changes needed in debugLogIn or doLogIn

    private fun debugLogIn(){
        startActivity(Intent(this, LandingPageActivity::class.java))
        finish() // Add finish() so user can't go back to login
    }

    private fun doLogIn() {
        val email = et_name.text.toString()
        val password = et_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchAndNavigateToLandingPage(auth.currentUser?.uid)
                } else {
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun fetchAndNavigateToLandingPage(uid: String?) {
        if (uid != null) {
            // Ensure db is initialized (should be by now)
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username") ?: "User"
                        val email = document.getString("email") ?: auth.currentUser?.email ?: ""
                        (application as AppData).username = username
                        (application as AppData).email = email
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LandingPageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish() // Finish LoginActivity
                    } else {
                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        // If auto-login failed due to missing data, need to show login UI again.
                        // This requires more complex handling, perhaps restarting the activity
                        // or directly calling setupLoginUI function. For now, it signs out.
                        // If this was called from autoLogin, we need to ensure splash dismisses:
                        isAutoLoginCheckComplete = true // Ensure splash dismisses if error happens here during auto-login
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    // Ensure splash dismisses if error happens here during auto-login
                    isAutoLoginCheckComplete = true
                }
        } else {
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show()
            // Ensure splash dismisses if error happens here during auto-login
            isAutoLoginCheckComplete = true
        }
    }

    private fun autoLogin(uid: String?) {
        // db should already be initialized before calling this
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    // === IMPORTANT: Set flag to true when Firestore check is done ===
                    isAutoLoginCheckComplete = true // Allow splash screen to dismiss

                    if (document.exists()) {
                        val username = document.getString("username") ?: "User"
                        val email = document.getString("email") ?: auth.currentUser?.email ?: ""
                        (application as AppData).username = username
                        (application as AppData).email = email
                        Toast.makeText(this, "AutoLogin Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LandingPageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish() // Finish LoginActivity
                    } else {
                        Toast.makeText(this, "User data not found. Signing out.", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        // Stay on LoginActivity - need to show the login form now.
                        // Since we returned early in onCreate, we need to call setContentView etc.
                        // OR simply restart the activity without the user being logged in.
                        // Easiest: Restart the activity
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener { e ->
                    // === IMPORTANT: Set flag to true when Firestore check is done ===
                    isAutoLoginCheckComplete = true // Allow splash screen to dismiss

                    Toast.makeText(this, "AutoLogin failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    // Stay on LoginActivity - need to show the login form now.
                    // Restart the activity
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
        } else {
            // This case shouldn't happen if called correctly, but handle defensively
            isAutoLoginCheckComplete = true // Allow splash screen to dismiss
            // Restart the activity to show login form
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}
