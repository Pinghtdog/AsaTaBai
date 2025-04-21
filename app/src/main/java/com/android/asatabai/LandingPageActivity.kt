package com.android.asatabai


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.asatabai.fragments.AccountsFragment
import com.android.asatabai.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.TextView

class LandingPageActivity : BaseActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        val username = intent.getStringExtra("name")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.frame,
                    HomeFragment()
                ) // Use replace and specify the container ID
                .commit()
        }

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navbarHome -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.navbarProfile -> {
                    replaceFragment(AccountsFragment())
                    true
                }

                else -> false
            }
        }

    }

    private  fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}