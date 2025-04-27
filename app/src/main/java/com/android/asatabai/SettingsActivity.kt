package com.android.asatabai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog
import android.content.Context
import android.widget.Switch
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.asatabai.utils.LocaleHelper
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale
import androidx.core.content.edit

class SettingsActivity : BaseActivity() {

    private lateinit var switcher: Switch
    private lateinit var auth: FirebaseAuth
    private lateinit var languageSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        handleNightMode()
        handleLanguage()
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
                        "- On the landing page, select 'DestinationsActivity' to find locations and their corresponding destination codes.\n" +
                        "- Select 'Jeepney Codes' to view available jeepney codes for different routes.\n\n" +
                        "2. Is this app free?\n" +
                        "- Yes, this app is completely free to use.\n\n" +
                        "3. How often are the codes updated?\n" +
                        "- We update the codes regularly to reflect any changes in Cebu's jeepney system.")
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

    private fun handleAboutTheApp() {
        val btnApp = findViewById<Button>(R.id.btnApp)
        btnApp.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("About The App")
                .setMessage("This app provides information about Cebu's jeepney routes, helping commuters navigate the city efficiently. It details different jeepney routes, making travel easier for locals and tourists. Stay updated with the latest route changes and find the best way to reach your destination.")
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleLogout() {
        val btnLogOut = findViewById<Button>(R.id.logout)
        btnLogOut.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    // Sign out from Firebase
                    auth.signOut()

                    // Reset language to English
                    LocaleHelper.setLocale(this, "en")
                    val preferences = getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
                    preferences.edit() { remove("Locale.Helper.Selected.Language") }

                    // Navigate to the Sign-in screen
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun handleNightMode() {
        switcher = findViewById(R.id.night_mode_switch)
        switcher.isChecked = nightMode

        switcher.setOnCheckedChangeListener { _, isChecked ->
            toggleNightMode(isChecked)
        }
    }

    private fun handleLanguage() {
        languageSpinner = findViewById(R.id.language_spinner)

        // Create array of language options
        val languages = arrayOf("English", "Filipino", "Cebuano")
        val languageCodes = arrayOf("en", "fil", "ceb") // Corresponding language codes

        // Create adapter for spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        // Set current language selection
        val currentLang = LocaleHelper.getPersistedData(this, Locale.getDefault().language)
        val position = when(currentLang) {
            "fil" -> 1
            "ceb" -> 2
            else -> 0 // Default to English
        }
        languageSpinner.setSelection(position)

        // Handle language selection
        languageSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedLanguageCode = languageCodes[position]
                if (selectedLanguageCode != currentLang) {
                    // Apply the new language and recreate activity
                    LocaleHelper.setLocale(this@SettingsActivity, selectedLanguageCode)
                    recreate()
                }
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    private fun handleAboutDevelopers() {
        val btn_login = findViewById<Button>(R.id.developer)
        btn_login.setOnClickListener {
            val intent = Intent(this, DevelopersActivity::class.java)
            startActivity(intent)
        }
    }
}