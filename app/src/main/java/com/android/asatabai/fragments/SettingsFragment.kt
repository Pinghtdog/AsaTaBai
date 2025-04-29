package com.android.asatabai.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.asatabai.utils.LocaleHelper
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale
import androidx.core.content.edit
import com.android.asatabai.AccountsActivity
import com.android.asatabai.BaseActivity
import com.android.asatabai.DevelopersActivity
import com.android.asatabai.LoginActivity
import com.android.asatabai.R

class SettingsFragment : Fragment() {

    private lateinit var switcher: Switch
    private lateinit var auth: FirebaseAuth
    private lateinit var languageSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        handleNightMode(view)
        handleLanguage(view)
        handleAboutDevelopers(view)
        handleAboutTheApp(view)
        handleLogout(view)
        handleContact(view)
        handleFAQ(view)
        handlEditProfile(view)
        return view
    }

    private fun handlEditProfile(view: View){
        val btnEditProfile = view.findViewById<Button>(R.id.editProfile)
        btnEditProfile.setOnClickListener {
            val intent = Intent(requireActivity(), AccountsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun handleFAQ(view: View) {
        val btnFAQ = view.findViewById<Button>(R.id.faq)
        btnFAQ.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("FAQ")
                .setMessage(
                    "Frequently Asked Questions:\n\n" +
                            "1. How do I use the app?\n" +
                            "- On the landing page, select 'DestinationsActivity' to find locations and their corresponding destination codes.\n" +
                            "- Select 'Jeepney Codes' to view available jeepney codes for different routes.\n\n" +
                            "2. Is this app free?\n" +
                            "- Yes, this app is completely free to use.\n\n" +
                            "3. How often are the codes updated?\n" +
                            "- We update the codes regularly to reflect any changes in Cebu's jeepney system."
                )
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleContact(view: View) {
        val btnSupport = view.findViewById<Button>(R.id.support)
        btnSupport.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Contact Support")
                .setMessage("For support, please reach out to us via:\n\n📧 Email: gumaponraziff@gmail.com & \n📞 Phone: +63 963 739 9301")
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleAboutTheApp(view: View) {
        val btnApp = view.findViewById<Button>(R.id.btnApp)
        btnApp.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("About The App")
                .setMessage("This app provides information about Cebu's jeepney routes, helping commuters navigate the city efficiently. It details different jeepney routes, making travel easier for locals and tourists. Stay updated with the latest route changes and find the best way to reach your destination.")
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleLogout(view: View) {
        val btnLogOut = view.findViewById<Button>(R.id.logout)
        btnLogOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    LocaleHelper.setLocale(requireContext(), "en")
                    val preferences = requireContext().getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
                    preferences.edit { remove("Locale.Helper.Selected.Language") }

                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun handleNightMode(view: View) {
        switcher = view.findViewById(R.id.night_mode_switch)
        switcher.isChecked = (activity as? BaseActivity)?.nightMode ?: false
        switcher.setOnCheckedChangeListener { _, isChecked ->
            (activity as? BaseActivity)?.toggleNightMode(isChecked)
        }
    }

    private fun handleLanguage(view: View) {
        languageSpinner = view.findViewById(R.id.language_spinner)

        val languages = arrayOf("English", "Filipino", "Cebuano")
        val languageCodes = arrayOf("en", "fil", "ceb")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        val currentLang = LocaleHelper.getPersistedData(requireContext(), Locale.getDefault().language)
        val position = when (currentLang) {
            "fil" -> 1
            "ceb" -> 2
            else -> 0
        }
        languageSpinner.setSelection(position)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, pos: Int, id: Long
            ) {
                val selectedLang = languageCodes[pos]
                if (selectedLang != currentLang) {
                    LocaleHelper.setLocale(requireContext(), selectedLang)
                    requireActivity().recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun handleAboutDevelopers(view: View) {
        val btnDev = view.findViewById<Button>(R.id.developer)
        btnDev.setOnClickListener {
            val intent = Intent(requireContext(), DevelopersActivity::class.java)
            startActivity(intent)
        }
    }
}
