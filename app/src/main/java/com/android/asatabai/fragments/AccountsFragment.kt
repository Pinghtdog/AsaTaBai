package com.android.asatabai.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.asatabai.R
import com.android.asatabai.SettingsActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountsFragment : Fragment() {
    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnEdit: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accounts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editUsername = view.findViewById(R.id.editUsername)
        editEmail = view.findViewById(R.id.editEmail)
        editPassword = view.findViewById(R.id.editPassword)
        btnEdit = view.findViewById(R.id.btnSave)

        disableEditing()

        val user = FirebaseAuth.getInstance().currentUser

//        if (user != null) {
//            val userId = user.uid
//            db.collection("users").document(userId).get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        editUsername.setText(document.getString("username"))
//                        editEmail.setText(user.email)
//                        editPassword.setText("")
//                    }
//                }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(), "Failed to load user info", Toast.LENGTH_SHORT).show()
//                }
//        } else {
//            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
//        }

        btnEdit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Edit")
                .setMessage("Are you sure you want to update your account information?")
                .setPositiveButton("Yes") { _, _ ->
                    updateUserInfo()
                }
                .setNegativeButton("No", null)
                .show()
        }

        onClickSettings(view)
    }

    private fun updateUserInfo() {
        val username = editUsername.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString()

        val user = auth.currentUser
        user?.let {
            val userId = it.uid

            // Update Firestore (No password stored here)
            db.collection("users").document(userId)
                .update("username", username)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Username updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to update username", Toast.LENGTH_SHORT).show()
                }

            // If email is changed, first reauthenticate the user, then update the email
            if (email.isNotEmpty() && email != user.email) {
                val credential = EmailAuthProvider.getCredential(user.email!!, password)

                // Re-authenticate
                user.reauthenticate(credential)
                    .addOnCompleteListener { reAuthTask ->
                        if (reAuthTask.isSuccessful) {
                            user.updateEmail(email)
                                .addOnSuccessListener {
                                    db.collection("users").document(userId)
                                        .update("email", email)
                                    Toast.makeText(requireContext(), "Email updated", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "Email update failed", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(requireContext(), "Re-authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            // Update password if provided (Only update in Firebase Authentication)
            if (password.isNotEmpty()) {
                user.updatePassword(password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Password update failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }


    private fun disableEditing() {
        // Set all fields to editable
        editUsername.isEnabled = true
        editEmail.isEnabled = true
        editPassword.isEnabled = true
    }

    private fun onClickSettings(view: View) {
        val btnSettings = view.findViewById<TextView>(R.id.imageView2)
        btnSettings?.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }
}
