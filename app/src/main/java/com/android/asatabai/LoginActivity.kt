package com.android.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.asatabai.data.JeepneyRoutes.JeepneyRoutesData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody

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
            val name = et_name.text.toString()
            val password = et_password.text.toString()
            if(name.isNotEmpty() && password.isNotEmpty()){
//                doLogin(this, name, password)
                debugLogin(this)
            } else{
                Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun debugLogin(loginActivity: LoginActivity) {
        val intent = Intent(loginActivity, LandingPageActivity :: class.java).apply {
            putExtra("name", "admin")
            putExtra("password", "admin")
        }
        startActivity(intent)
    }

    private fun doLogin(loginActivity: LoginActivity, name: String, password: String) {

        val intent = Intent(loginActivity, LandingPageActivity :: class.java).apply {
            putExtra("name", name)
            putExtra("password", password)
        }
        startActivity(intent)
        CoroutineScope(Dispatchers.IO).launch{
            val client = OkHttpClient()
            val mediaType = "application/json".toMediaType()
            val body = RequestBody.create(
                mediaType,
                """{
                    "username": "$name",
                    "password": "$password"
                }""".trimIndent()
            )

            val request = okhttp3.Request.Builder()
                .url("https://exultant-mature-dibble.glitch.me/login")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    Log.e("Response Successful", "${response.body?.toString()}")
                    val intent = Intent(loginActivity, LandingPageActivity :: class.java).apply {
                        putExtra("name", name)
                        putExtra("password", password)
                    }
                    startActivity(intent)
                }else{
                    withContext(Dispatchers.Main) {
                        Log.e("Response Failed", "Failed: ${response.code} - ${response.message}")
                        Toast.makeText(loginActivity, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("Response Failed", "Error: ${e.message}")
                    Toast.makeText(loginActivity, "Unable to complete the request this time!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}