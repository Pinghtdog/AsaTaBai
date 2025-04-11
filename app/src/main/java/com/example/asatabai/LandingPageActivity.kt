package com.example.asatabai


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingPageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)
        handleJeepneyCodes();
        handleLogout();
    }

    private fun handleJeepneyCodes(){
        val btnJeepney = findViewById<Button>(R.id.button7)
        btnJeepney.setOnClickListener{
            startActivity(Intent(this,JeepneyCodesActivity::class.java))
        }
    }
    private fun handleLogout(){
        val btnAccounts = findViewById<Button>(R.id.accountsbtn)
        btnAccounts.setOnClickListener {
            val intent = Intent(this, AccountsPageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}