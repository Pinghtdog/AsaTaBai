package com.android.asatabai

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class DevelopersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developers)


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

        handleLandingPage()
        handleLogout()
    }

//    private fun handleDevelopers() {
//        val listView: ListView = findViewById(R.id.developerListView)
//        val developers = listOf(
//            Developer(
//                "Raziff D. Gumapon",
//                "A passionate computer science student from the Philippines. Born on April 5, 2005, he is currently diving deep into programming. He has experience with C, C++, and Java.",
//                R.drawable.raziff
//            ),
//            Developer(
//                "Kelvin Pehrson P. Kierulf",
//                "A dedicated computer science student focused on leveraging mobile technology to create smarter, more reliable commuting experiences for both drivers and passengers.",
//                R.drawable.kp
//            )
//        )
//
//        val adapter = DeveloperAdapter(this, developers)
//        listView.adapter = adapter
//    }

    private fun handleLandingPage() {
        val btnHome = findViewById<Button>(R.id.home)
        btnHome.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLogout() {
        val btnLogOut = findViewById<Button>(R.id.logout)
        btnLogOut.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    // Navigate to the Sign-in screen
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish() // Close current activity
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}