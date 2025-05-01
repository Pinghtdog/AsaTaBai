package com.android.asatabai // Replace with your app's package name

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.asatabai.LoginActivity // Import your LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    // Duration the splash screen will be displayed (including animation time)
    private val SPLASH_DISPLAY_LENGTH: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for this activity
        setContentView(R.layout.activity_splash_screen) // You will create this layout file

        // Find the ImageView for your jeepney
        val jeepneyImageView: ImageView = findViewById(R.id.splash_jeepney_icon) // Make sure this ID matches your layout

        // Load your custom animation
        // You will create an XML animation file (e.g., res/anim/jeepney_animation.xml)
        val customAnimation = AnimationUtils.loadAnimation(this, R.anim.jeepney_animation)

        // Start the animation on the ImageView
        jeepneyImageView.startAnimation(customAnimation)

        // Use a Handler to transition to the next activity after the splash duration
        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the LoginActivity. */
            val mainIntent = Intent(this, LoginActivity::class.java) // Go to your LoginActivity
            startActivity(mainIntent)

            /* Finish the splash activity so it can't be returned to */
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
