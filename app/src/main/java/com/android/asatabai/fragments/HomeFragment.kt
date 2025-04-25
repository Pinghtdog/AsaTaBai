package com.android.asatabai.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.asatabai.JeepneyCodesActivity
import com.android.asatabai.LoginActivity
import com.android.asatabai.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Find all views (non-nullable)
        val btnJeepneyCodes = view.findViewById<Button>(R.id.btnJeepneyCodes)
        val btnDestinations = view.findViewById<Button>(R.id.btnDestinations)
        val textMessage = view.findViewById<TextView>(R.id.textmessage)

        // Optional: Retrieve username passed via arguments
        val username = arguments?.getString("username")

        // Display welcome message using authenticated user
        displayWelcomeMessage(textMessage)

        // Button click listeners
        setupJeepneyCodesButton(btnJeepneyCodes)
        setupDestinationsButton(btnDestinations)
    }

    private fun displayWelcomeMessage(tvWelcome: TextView) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userUsername = document.getString("username") ?: "User"
                        tvWelcome.text = "Welcome back,\n$userUsername!"

                        val email = document.getString("email")
                        Log.d("UserData", "Email: $email")
                    } else {
                        tvWelcome.text = "Welcome!"
                        Log.w("Firestore", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    tvWelcome.text = "Welcome!"
                    Log.w("Firestore", "Error getting document", e)
                }
        } else {
            // User not logged in, go to LoginActivity and finish current activity
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setupJeepneyCodesButton(button: Button) {
        button.setOnClickListener {
            startActivity(Intent(requireContext(), JeepneyCodesActivity::class.java))
        }
    }

    private fun setupDestinationsButton(button: Button) {
        button.setOnClickListener {
            Toast.makeText(requireContext(), "Destinations Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
