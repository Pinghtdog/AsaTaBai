package com.android.asatabai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject

class AccountsPageActivity : Activity() {
    lateinit var firstnameTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_accounts)

        firstnameTextView = findViewById(R.id.textView)

        onClickSettings()
        var username = ""
        var password = ""
        intent?.getStringExtra("name")?.let { name ->
            username = name
            firstnameTextView.text = username
        }
        intent?.getStringExtra("password")?.let { pw ->
            password = pw
        }
        if(username.isNotEmpty() && password.isNotEmpty()){
            fetchUserData(this, username, password)
        } else {
            Toast.makeText(this, "Username and password information are missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserData(accountsPageActivity: AccountsPageActivity, username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val mediaType = "application/json".toMediaType()

            val body = RequestBody.create(
                mediaType,
                """
                   {
                       "username": "$username",
                       "password": "$password"
                   }
                """.trimIndent()
            )

            val request = okhttp3.Request.Builder()
                .url("https://exultant-mature-dibble.glitch.me/get-profile")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val jsonResponse = JSONObject(responseBody)
                        val profile_info = jsonResponse.getJSONObject("profile-info")
                        withContext(Dispatchers.Main) {
                            val firstname = profile_info.optString("firstname")
                            val middlename = profile_info.optString("middlename")
                            val lastname = profile_info.optString("lastname")

                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(accountsPageActivity, "Unable to fetch profile information!", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(accountsPageActivity, "Error encountered!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun onClickSettings(){
        val btnSettings = findViewById<TextView>(R.id.textSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}