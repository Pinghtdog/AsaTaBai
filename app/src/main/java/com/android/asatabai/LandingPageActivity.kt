package com.android.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LandingPageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)
        handleJeepneyCodes();
        handleAccounts()
    }

    private fun handleJeepneyCodes(){
        val btnJeepney = findViewById<Button>(R.id.button7)
        btnJeepney.setOnClickListener{
            startActivity(Intent(this,JeepneyCodesActivity::class.java))
        }
    }
    private fun handleAccounts(){
        val btnAccounts = findViewById<Button>(R.id.accountsbtn)
        val textMessage = findViewById<TextView>(R.id.textMessage)
        var username = ""
        var password = ""
        intent?.getStringExtra("name")?.let { name ->
            textMessage.text = "Welcome back $name!"
            username = name
        }
        intent?.getStringExtra("password")?.let { pw ->
            password = pw
        }
        btnAccounts.setOnClickListener {
            val intent = Intent(this, AccountsPageActivity::class.java).apply {
                putExtra("name", username)
                putExtra("password", password)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}