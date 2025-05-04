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
import androidx.constraintlayout.widget.ConstraintLayout // Import the layout type if you're using ConstraintLayout
// import androidx.LinearLayout.widget.LinearLayout // Uncomment if your list_item_settings_nav.xml root is LinearLayout

class SettingsFragment : Fragment() {

    private lateinit var switcher: SwitchCompat
    private lateinit var auth: FirebaseAuth
    private lateinit var languageSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // --- Customize and Handle Clicks for List Items ---

        // Edit Profile Item
        val editProfileItem = view.findViewById<ConstraintLayout>(R.id.editProfile) // Use the ID from the include tag
        val editProfileTitleTextView = editProfileItem.findViewById<TextView>(R.id.settingTitleTextView)
        val editProfileIconImageView = editProfileItem.findViewById<ImageView>(R.id.iconImageView)

        editProfileTitleTextView.text = getString(R.string.edit_profile)
        editProfileIconImageView.setImageResource(R.drawable.account) // Replace with your desired icon
        editProfileItem.setOnClickListener {
            val intent = Intent(requireActivity(), AccountsActivity::class.java)
            startActivity(intent)
        }

        // FAQ Item (assuming you gave it a unique ID in your XML, e.g., settingItemFaq)
        val faqItem = view.findViewById<ConstraintLayout>(R.id.faq) // Make sure you have this ID in XML
        val faqTitleTextView = faqItem.findViewById<TextView>(R.id.settingTitleTextView)
        val faqIconImageView = faqItem.findViewById<ImageView>(R.id.iconImageView)

        faqTitleTextView.text = getString(R.string.faq)
        faqIconImageView.setImageResource(R.drawable.faqicon) // Replace with your desired icon
        faqItem.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.faq_title))
                .setMessage(getString(R.string.faq_message))
                .setPositiveButton("Ok", null)
                .show()
        }

        // Contact Support Item (assuming you gave it a unique ID, e.g., settingItemSupport)
        val supportItem = view.findViewById<ConstraintLayout>(R.id.support) // Make sure you have this ID in XML
        val supportTitleTextView = supportItem.findViewById<TextView>(R.id.settingTitleTextView)
        val supportIconImageView = supportItem.findViewById<ImageView>(R.id.iconImageView)

        supportTitleTextView.text = getString(R.string.contact_support)
        supportIconImageView.setImageResource(R.drawable.supporticon) // Replace with your desired icon
        supportItem.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.support_title))
                .setMessage(getString(R.string.support_message))
                .setPositiveButton("Ok", null)
                .show()
        }

        // About the App Item (assuming you gave it a unique ID, e.g., settingItemAboutApp)
        val aboutAppItem = view.findViewById<ConstraintLayout>(R.id.about) // Make sure you have this ID in XML
        val aboutAppTitleTextView = aboutAppItem.findViewById<TextView>(R.id.settingTitleTextView)
        val aboutAppIconImageView = aboutAppItem.findViewById<ImageView>(R.id.iconImageView)

        aboutAppTitleTextView.text = getString(R.string.about_the_app)
        aboutAppIconImageView.setImageResource(R.drawable.abouticon) // Replace with your desired icon
        aboutAppItem.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.about_app_title))
                .setMessage(getString(R.string.about_app_message))
                .setPositiveButton("Ok", null)
                .show()
        }

        // About the Developers Item (assuming you gave it a unique ID, e.g., settingItemDevelopers)
        val developersItem = view.findViewById<ConstraintLayout>(R.id.developer) // Make sure you have this ID in XML
        val developersTitleTextView = developersItem.findViewById<TextView>(R.id.settingTitleTextView)
        val developersIconImageView = developersItem.findViewById<ImageView>(R.id.iconImageView)

        developersTitleTextView.text = getString(R.string.about_the_developers)
        developersIconImageView.setImageResource(R.drawable.developersicon) // Replace with your desired icon
        developersItem.setOnClickListener {
            val intent = Intent(requireContext(), DevelopersActivity::class.java)
            startActivity(intent)
        }


        // --- Keep your existing code for Night Mode, Language, and Logout ---

        handleNightMode(view) // You can keep these separate if you prefer
        handleLanguage(view)
        handleLogout(view) // Assuming logout is still a separate Button

        // You can remove the now empty handle... functions for the list items you moved here
        // and also remove the innitializeIcons() function entirely.
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

    private fun handleLogout(view: View) {
        val btnLogOut = view.findViewById<ImageView>(R.id.logout)
        btnLogOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout_title))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    auth.signOut()
                    // It's generally better to let your app determine the default language
                    // based on the device or your app's preferences, rather than
                    // hardcoding "en" here after logout.
                    LocaleHelper.setLocale(requireContext(), "en") // Consider removing or making this dynamic
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

    // Remove the empty handle functions that are now handled in onViewCreated
    // private fun handleEditProfile(view: View){}
    // private fun handleFAQ(view: View) {}
    // private fun handleContact(view: View) {}
    // private fun handleAboutTheApp(view: View) {}
    // private fun handleAboutDevelopers(view: View) {}

    // Remove the innitializeIcons function entirely
    // private fun innitializeIcons(){}
}