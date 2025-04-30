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
import com.android.asatabai.DestinationsActivity
import com.android.asatabai.JeepneyCodesActivity
import com.android.asatabai.LoginActivity
import com.android.asatabai.R
import com.android.asatabai.data.AppData
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

        // Display welcome message using authenticated user
        displayWelcomeMessage(textMessage)

        // Button click listeners
        setupJeepneyCodesButton(btnJeepneyCodes)
        setupDestinationsButton(btnDestinations)
    }

    private fun displayWelcomeMessage(tvWelcome: TextView) {
        val username = (requireActivity().application as AppData).username
        tvWelcome.text = getString(R.string.welcome_user, username)
    }

    private fun setupJeepneyCodesButton(button: Button) {
        button.setOnClickListener {
            startActivity(Intent(requireContext(), JeepneyCodesActivity::class.java))
        }
    }

    private fun setupDestinationsButton(button: Button) {
        button.setOnClickListener {
            startActivity(Intent(requireContext(), DestinationsActivity::class.java))
            //Toast.makeText(requireContext(), "DestinationsActivity Clicked", Toast.LENGTH_SHORT).show()
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
