package com.android.asatabai

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView

class DevelopersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developers)
        val btnBack = findViewById<ImageView>(R.id.back)
        btnBack.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java).apply {
                putExtra("SELECTED_FRAGMENT", "SETTINGS")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
        val developers = listOf(
            Developer(
                "Raziff D. Gumapon",
                "Raziff D. Gumapon is a passionate computer science student from the Philippines. Born on April 5, 2005, he is currently diving deep into the world of programming and technology. He has experience working with languages like C, C++, and Java, and is always eager to learn more and improve his skills. He is excited about the future of software development and look forward to exploring new challenges and opportunities in the field.",
                R.drawable.raziff
            ),
            Developer(
                "Kelvin Pehrson P. Kierulf",
                "He is a  dedicated computer science student with a passion for developing innovative solutions through technology. My goal is to leverage mobile technology to create smarter, more reliable commuting experiences for both drivers and passengers.My ambition is to contribute to the digital transformation of local transportation systems, making them more modern, efficient, and user-friendly.",
                R.drawable.kp
            )
        )

        val adapter = DeveloperAdapter(this, developers)
        findViewById<ListView>(R.id.developersListView).adapter = adapter

    }

}