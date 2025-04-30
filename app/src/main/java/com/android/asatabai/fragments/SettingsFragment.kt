package com.android.asatabai.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
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
import com.android.asatabai.data.AppData

class SettingsFragment : Fragment() {

    private lateinit var switcher: SwitchCompat
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
        handleEditProfile(view)
        return view
    }

    private fun handleEditProfile(view: View){
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
                .setTitle(getString(R.string.faq_title))
                .setMessage(getString(R.string.faq_message))
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleContact(view: View) {
        val btnSupport = view.findViewById<Button>(R.id.support)
        btnSupport.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.support_title))
                .setMessage(getString(R.string.support_message))
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleAboutTheApp(view: View) {
        val btnApp = view.findViewById<Button>(R.id.btnApp)
        btnApp.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.about_app_title))
                .setMessage(getString(R.string.about_app_message))
                .setPositiveButton("Ok", null)
                .show()
        }
    }

    private fun handleLogout(view: View) {
        val btnLogOut = view.findViewById<Button>(R.id.logout)
        btnLogOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout_title))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    auth.signOut()
                    LocaleHelper.setLocale(requireContext(), "en")
                    val preferences = requireContext().getSharedPreferences("APP_PREF", Context.MODE_PRIVATE)
                    preferences.edit { remove("Locale.Helper.Selected.Language") }

                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton(getString(R.string.no), null)
                .show()
        }
    }

    private fun handleNightMode(view: View) {
        switcher = view.findViewById(R.id.night_mode_switch)
        switcher.isChecked = (requireActivity().application as AppData).isNightMode
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
