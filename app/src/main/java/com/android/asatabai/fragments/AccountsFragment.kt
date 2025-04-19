package com.android.asatabai.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog // Use androidx.appcompat.app.AlertDialog for consistency with AppCompatActivity
import androidx.fragment.app.Fragment // Import Fragment
import com.android.asatabai.DevelopersActivity
import com.android.asatabai.LoginActivity
import com.android.asatabai.R

import android.widget.TextView
import android.widget.Toast
import com.android.asatabai.LandingPageActivity // Assuming this is needed for navigation if onClickSettings navigates there (unlikely, but keep for reference)
import com.android.asatabai.SettingsActivity // Assuming SettingsActivity exists
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject

class AccountsFragment : Fragment() {
    private lateinit var firstnameTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accounts, container, false) // Inflate your settings layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstnameTextView = view.findViewById(R.id.textView)

        // Get the username and password from the fragment's arguments
        // These arguments should be passed from the hosting Activity (LandingPageActivity)
        val username = arguments?.getString("name")
        val password = arguments?.getString("password") // SECURITY WARNING: Avoid passing passwords like this!

        firstnameTextView.text = username

//        if (!username.isNullOrEmpty()) { // Check if username is not null or empty
//            // Display the username immediately if available
//            firstnameTextView.text = username
//
//            // Fetch user data if both username and password are provided (with security considerations)
//            // You might want to fetch data based on a user ID or token instead of password
//            if (!password.isNullOrEmpty()) {
//                fetchUserData(username, password)
//            } else {
//                // Handle case where password is unexpectedly missing but username is present
//                // You might still want to fetch data based on username or show partial info
//                Toast.makeText(requireContext(), "Password information missing for data fetch.", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            // Handle case where username is missing
//            firstnameTextView.text = "Guest User" // Or some default text
//            Toast.makeText(requireContext(), "Username information is missing.", Toast.LENGTH_SHORT).show()
//        }


        // Set up the settings button click listener
        onClickSettings(view)

    }

    private fun fetchUserData(username: String, password: String) { // Removed the Activity parameter
        // Ensure this network call is handled appropriately, especially regarding security
        // Consider using tokens or other secure methods instead of username/password here
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val mediaType = "application/json".toMediaType()

            val body = RequestBody.create(
                mediaType,
                """
                   {
                       "username": "$username",
                       "password": "$password"
                   }
                """.trimIndent()
            )

            val request = okhttp3.Request.Builder()
                .url("https://exultant-mature-dibble.glitch.me/get-profile")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val jsonResponse = JSONObject(responseBody)
                        val profile_info = jsonResponse.optJSONObject("profile-info") // Use optJSONObject for safety
                        if (profile_info != null) { // Check if "profile-info" exists
                            withContext(Dispatchers.Main) {
                                val firstname = profile_info.optString("firstname")
                                val middlename = profile_info.optString("middlename")
                                val lastname = profile_info.optString("lastname")

                                // Update UI with fetched data (e.g., update TextView)
                                // firstnameTextView.text = "$firstname $middlename $lastname" // Example

                                // If you want to update the initial TextView that already shows username,
                                // you might decide whether to show just the username or the full name here.
                                // For example, update the text if firstname is available:
                                if (firstname.isNotEmpty()) {
                                    firstnameTextView.text = "Welcome, $firstname!"
                                } else {
                                    // Fallback if firstname is not available in profile-info
                                    firstnameTextView.text = "Welcome, $username!"
                                }


                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Profile information not found in response.", Toast.LENGTH_LONG).show()
                            }
                        }
                    } ?: withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Empty response body!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "API call failed: ${response.code}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error fetching data: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace() // Print stack trace for debugging
                }
            }
        }
    }


    // onClickSettings needs access to the inflated view to find the button
    private fun onClickSettings(view: View){
        val btnSettings = view.findViewById<TextView>(R.id.textSettings) // Find the view using the inflated 'view'

        // Use the safe call operator ?. to avoid NullPointerException if textSettings isn't found
        btnSettings?.setOnClickListener {
            // Start Activity from the fragment's context or the activity context
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            // Optional flags - consider if you really need CLEAR_TASK here or handle navigation differently
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } ?: run {
            // This block executes if btnSettings is null (ID not found in layout)
            Toast.makeText(requireContext(), "Settings button not found in layout!", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_PASSWORD = "password" // Again, security warning for password

        @JvmStatic
        fun newInstance(name: String, password: String? = null): AccountsFragment { // Made password nullable
            return AccountsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_PASSWORD, password) // Still passing password, but consider alternatives
                }
            }
        }
    }



}