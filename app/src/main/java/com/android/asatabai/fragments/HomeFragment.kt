package com.android.asatabai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.asatabai.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find all the relevant views within the fragment's layout
        val btnJeepneyCodes = view.findViewById<Button>(R.id.btnJeepneyCodes)
        val btnDestinations = view.findViewById<Button>(R.id.btnDestinations)
        val textMessage = view.findViewById<TextView>(R.id.textmessage)
        val username = arguments?.getString("username")

        // debugging purposes
        textMessage.text = "Welcome back $username!"

        // Setup the click listeners f or all buttons
        setupJeepneyCodesButton(btnJeepneyCodes)
        setupDestinationsButton(btnDestinations)
    }

    // --- Listener Setup Functions ---

    private fun setupJeepneyCodesButton(button: Button) {
        button.setOnClickListener {
            // Navigate to Jeepney Codes screen
//            val intent = Intent(requireContext(), JeepneyCodesActivity::class.java)
//            startActivity(intent)
            button.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame, JeepneyCodesFragment())  // Replace the activity's container
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupDestinationsButton(button: Button) {
        button.setOnClickListener {
            // Placeholder: Implement navigation or action for Destinations
            Toast.makeText(requireContext(), "Destinations Clicked", Toast.LENGTH_SHORT).show()
            // Example:
            // val intent = Intent(requireContext(), DestinationsActivity::class.java)
            // startActivity(intent)
        }
    }



    // --- Companion Object ---
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