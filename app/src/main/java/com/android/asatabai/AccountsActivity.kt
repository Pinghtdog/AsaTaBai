package com.android.asatabai

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.android.asatabai.data.AppData
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountsActivity : BaseActivity() {
    private lateinit var editUsername: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editConfirmPassword: EditText
    private lateinit var currentPassword: EditText
    private lateinit var btnEdit: Button
    private lateinit var btnBack: ImageView
    private lateinit var appData: AppData

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)

        editUsername = findViewById(R.id.editUsername)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        currentPassword = findViewById(R.id.currentPassword) // <- make sure this exists in your XML
        btnEdit = findViewById(R.id.btnSave)
        btnBack = findViewById(R.id.imageView3)

        appData = application as AppData
        editUsername.setText(appData.username)
        editEmail.setText(appData.email)

        btnEdit.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirm Edit")
                .setMessage("Are you sure you want to update your account information?")
                .setPositiveButton("Yes") { _, _ ->
                    if (validateInputs()) {
                        reauthenticateAndUpdate()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, LandingPageActivity::class.java).apply {
                putExtra("SELECTED_FRAGMENT", "SETTINGS")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        val newPassword = editPassword.text.toString()
        val confirmPassword = editConfirmPassword.text.toString()
        val current = currentPassword.text.toString()

        if (current.isEmpty()) {
            Toast.makeText(this, "Please enter your current password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (newPassword.isNotEmpty() || confirmPassword.isNotEmpty()) {
            if (newPassword != confirmPassword) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
                return false
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "New password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun reauthenticateAndUpdate() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "No user is currently logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val currentPass = currentPassword.text.toString()
        val credential = EmailAuthProvider.getCredential(user.email!!, currentPass)

        user.reauthenticate(credential)
            .addOnCompleteListener { reAuthTask ->
                if (reAuthTask.isSuccessful) {
                    updateUserInfo()
                } else {
                    Toast.makeText(this, "Re-authentication failed. Please check your current password.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserInfo() {
        val username = editUsername.text.toString().trim()
        val newEmail = editEmail.text.toString().trim()
        val newPassword = editPassword.text.toString()
        val user = auth.currentUser ?: return
        val userId = user.uid

        // Update username in Firestore
        db.collection("users").document(userId)
            .update("username", username)
            .addOnSuccessListener {
                appData.username = username
                Toast.makeText(this, "Username updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update username", Toast.LENGTH_SHORT).show()
            }

        // Check if email changed
        if (newEmail.isNotEmpty() && newEmail != user.email) {
            user.updateEmail(newEmail)
                .addOnSuccessListener {
                    db.collection("users").document(userId)
                        .update("email", newEmail)
                        .addOnSuccessListener {
                            appData.email = newEmail
                            Toast.makeText(this, "Email updated", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update email", Toast.LENGTH_SHORT).show()
                }
        }

        // Check if password needs update
        if (newPassword.isNotEmpty()) {
            user.updatePassword(newPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show()
                    editPassword.text.clear()
                    editConfirmPassword.text.clear()
                    currentPassword.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
