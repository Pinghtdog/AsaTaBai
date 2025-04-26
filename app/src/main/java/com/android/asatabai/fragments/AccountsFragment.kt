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
import com.android.asatabai.data.AppData
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountsFragment : Fragment() {
    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editConfirmPassword: EditText
    private lateinit var btnEdit: Button
    private lateinit var appData: AppData

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
        editConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        btnEdit = view.findViewById(R.id.btnSave)

        disableEditing()

        // Get from AppData
        appData = requireActivity().application as AppData
        editUsername.setText(appData.username)
        editEmail.setText(appData.email)
        editPassword.setText("")
        editConfirmPassword.setText("")

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        btnEdit.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Edit")
                .setMessage("Are you sure you want to update your account information?")
                .setPositiveButton("Yes") { _, _ ->
                    if (validateInputs()) {
                        updateUserInfo()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }

        onClickSettings(view)
    }

    private fun validateInputs(): Boolean {
        val password = editPassword.text.toString()
        val confirmPassword = editConfirmPassword.text.toString()

        // Only validate passwords if they're being changed (not empty)
        if (password.isNotEmpty() || confirmPassword.isNotEmpty()) {
            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
                return false
            }

            if (password.length < 6) {
                Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun updateUserInfo() {
        val username = editUsername.text.toString().trim()
        val email = editEmail.text.toString().trim()
        val password = editPassword.text.toString()

        val user = auth.currentUser
        user?.let {
            val userId = it.uid

            // Update username in Firestore and AppData
            db.collection("users").document(userId)
                .update("username", username)
                .addOnSuccessListener {
                    appData.username = username
                    Toast.makeText(requireContext(), "Username updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to update username", Toast.LENGTH_SHORT).show()
                }

            // Proceed only if email/password is changing AND password is entered
            val needEmailUpdate = email.isNotEmpty() && email != user.email
            val needPasswordUpdate = password.isNotEmpty()

            if ((needEmailUpdate || needPasswordUpdate) && user.email != null) {
                if (password.isEmpty()) {
                    Toast.makeText(requireContext(), "Password required to update email or password", Toast.LENGTH_LONG).show()
                    return@let
                }

                val credential = EmailAuthProvider.getCredential(user.email!!, password)

                user.reauthenticate(credential)
                    .addOnCompleteListener { reAuthTask ->
                        if (reAuthTask.isSuccessful) {
                            if (needEmailUpdate) {
                                user.updateEmail(email)
                                    .addOnSuccessListener {
                                        db.collection("users").document(userId)
                                            .update("email", email)
                                            .addOnSuccessListener {
                                                appData.email = email
                                                Toast.makeText(requireContext(), "Email updated", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(requireContext(), "Email update failed", Toast.LENGTH_SHORT).show()
                                    }
                            }

                            if (needPasswordUpdate) {
                                user.updatePassword(password)
                                    .addOnSuccessListener {
                                        Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT).show()
                                        // Clear password fields after successful update
                                        editPassword.text.clear()
                                        editConfirmPassword.text.clear()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(requireContext(), "Password update failed", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(requireContext(), "Re-authentication failed. Please check your current password.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun disableEditing() {
        // Set all fields to editable
        editUsername.isEnabled = true
        editEmail.isEnabled = true
        editPassword.isEnabled = true
        editConfirmPassword.isEnabled = true
    }

    private fun onClickSettings(view: View) {
        val btnSettings = view.findViewById<ImageView>(R.id.imageView2)
        btnSettings?.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }
}