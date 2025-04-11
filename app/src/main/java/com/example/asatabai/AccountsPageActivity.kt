package com.example.asatabai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class AccountsPageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts_page)
        onClickSettings()
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