package com.android.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog

class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        handleNightMode()
        handleEditProfile();
        handleLanguage()
        handleNotifications()
        handleAboutDevelopers()
        handleAboutTheApp()
        handleLogout()
        handleContact()
        handleFAQ()
    }

    private fun handleFAQ() {
        val btnFAQ = findViewById<Button>(R.id.faq)
        btnFAQ.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("FAQ")
                .setMessage("Frequently Asked Questions:\n\n" +
                        "1. How do I use the app?\n" +
                        "- On the landing page, select 'Destinations' to find locations and their corresponding destination codes.\n" +
                        "- Select 'Jeepney Codes' to view available jeepney codes for different routes.\n\n" +
                        "2. Is this app free?\n" +
                        "- Yes, this app is completely free to use.\n\n" +
                        "3. How often are the codes updated?\n" +
                        "- We update the codes regularly to reflect any changes in Cebu’s jeepney system.")
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleContact() {
        val btnSupport = findViewById<Button>(R.id.support)
        btnSupport.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Contact Support")
                .setMessage("For support, please reach out to us via:\n\n📧 Email: gumaponraziff@gmail.com & \n📞 Phone: +63 963 739 9301")
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleAboutTheApp(){
        val btnApp = findViewById<Button>(R.id.btnApp)
        btnApp.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("About The App")
                .setMessage("This app provides information about Cebu's jeepney routes, helping commuters navigate the city efficiently. It details different jeepney routes, making travel easier for locals and tourists. Stay updated with the latest route changes and find the best way to reach your destination.")
                .setPositiveButton("Ok", null)
                .show()
        }
    }
    private fun handleEditProfile() {
        val btnEditProfile = findViewById<Button>(R.id.editProfile)
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, AccountsPageActivity :: class.java)
            startActivity(intent)
        }
    }

    private fun handleLogout(){
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

    private fun handleNightMode() {

    }

    private fun handleLanguage() {

    }

    private fun handleNotifications() {

    }

    private fun handleAboutDevelopers() {
        val btn_login = findViewById<Button>(R.id.developer)
        btn_login.setOnClickListener {
            val intent = Intent(this, DevelopersActivity :: class.java)
            startActivity(intent)
        }
    }
}