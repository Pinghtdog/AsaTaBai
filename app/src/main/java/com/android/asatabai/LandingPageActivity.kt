package com.android.asatabai

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.asatabai.fragments.HomeFragment
import com.android.asatabai.fragments.MapFragment
import com.android.asatabai.fragments.RecentsFragment
import com.android.asatabai.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class LandingPageActivity : BaseActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        val selectedFragment = intent?.getStringExtra("SELECTED_FRAGMENT")

        if (savedInstanceState == null) {
            val initialFragment = when (selectedFragment) {
                "SETTINGS" -> SettingsFragment()
                else -> HomeFragment() // default fragment
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, initialFragment)
                .commit()
        }

        bottomNavView = findViewById(R.id.bottomNavigationView)

        selectedFragment?.let {
            val navItemId = when (it) {
                "SETTINGS" -> R.id.navbarSettings
                else -> R.id.navbarHome
            }
            bottomNavView.selectedItemId = navItemId
        }

        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navbarHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navbarSettings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                R.id.navbarSearch -> {
                    replaceFragment(RecentsFragment())
                    true
                }
                R.id.navbarMap -> {
                    replaceFragment(MapFragment())
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