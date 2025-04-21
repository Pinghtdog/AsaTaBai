package com.android.asatabai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody

class RegisterActivity : BaseActivity() {
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
        val etFirstname = findViewById<EditText>(R.id.editTextFirstname)
        val etMiddleName = findViewById<EditText>(R.id.editTextMiddleName)
        val etLastname = findViewById<EditText>(R.id.editTextLastname)
        btn_login.setOnClickListener {
            val name = etName.text.toString()
            val password = etPass.text.toString()
            val confirm = etConfirm.text.toString()
            val firstname = etFirstname.text.toString()
            val middlename = etMiddleName.text.toString()
            val lastname = etLastname.text.toString()

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

            if (firstname.isEmpty()) {
                Toast.makeText(this, "First name cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!firstname.matches(Regex("^[a-zA-Z]+$"))) {
                Toast.makeText(this, "First name can only contain letters", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (middlename.isNotEmpty() && !middlename.matches(Regex("^[a-zA-Z]+$"))) {
                Toast.makeText(this, "Middle name can only contain letters", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (lastname.isEmpty()) {
                Toast.makeText(this, "Last name cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!lastname.matches(Regex("^[a-zA-Z]+$"))) {
                Toast.makeText(this, "Last name can only contain letters", Toast.LENGTH_LONG).show()
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

            saveRegistration(this, name, password, firstname, middlename, lastname)
        }
    }

    private fun saveRegistration(registerActivity: RegisterActivity, name: String, password: String, firstname: String, middlename: String, lastname: String) {
        val profile_info = """{
            "firstname": "$firstname",
            "middlename": "$middlename",
            "lastname": "$lastname"
        }""".trimIndent()
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val mediaType = "application/json".toMediaType()
            val body = RequestBody.create(
                mediaType,
                """{
                    "username": "$name",
                    "password": "$password",
                    "profile_info": $profile_info
                }""".trimMargin()
            )
            val request = okhttp3.Request.Builder()
                .url("https://exultant-mature-dibble.glitch.me/save-profile")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            try{
                val response = client.newCall(request).execute()

                if (response.isSuccessful){
                    val responseBody = response.body?.string()
                    withContext(Dispatchers.Main){
                        Log.e("Response Successful", "$responseBody")
                        Toast.makeText(registerActivity, "Sign Up Successful", Toast.LENGTH_LONG).show()
                        val intent = Intent(registerActivity, LoginActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("password", password)
                            putExtra("firstname", firstname)
                        }
                        startActivity(intent)
                    }
                } else {
                    withContext(Dispatchers.Main){
                        Log.e("Response Failed", "${response.code}: ${response.message}")
                        Toast.makeText(registerActivity, "Saving profile failed", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.e("Response Failed", "Error: ${e.message}")
                    Toast.makeText(registerActivity, "Unable to complete the action now!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}