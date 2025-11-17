package com.example.edunomad

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var notificationsSwitch: SwitchMaterial

    private val prefs by lazy { getSharedPreferences("app_settings", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        profileImage = findViewById(R.id.settings_profile_image)
        nameText = findViewById(R.id.settings_name)
        emailText = findViewById(R.id.settings_email)
        themeSwitch = findViewById(R.id.switch_theme)
        notificationsSwitch = findViewById(R.id.switch_notifications)

        // Load current user info from Firebase
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            nameText.text = user.displayName ?: "User"
            emailText.text = user.email ?: "No email"
            if (user.photoUrl != null) {
                Glide.with(this).load(user.photoUrl).into(profileImage)
            } else {
                profileImage.setImageResource(R.drawable.img_15)
            }
        }

        // Load saved theme
        val isDarkTheme = prefs.getBoolean("dark_theme", false)
        themeSwitch.isChecked = isDarkTheme
        applyTheme(isDarkTheme)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_theme", isChecked).apply()
            applyTheme(isChecked)
        }

        // Notifications toggle (example)
        val notificationsEnabled = prefs.getBoolean("notifications", true)
        notificationsSwitch.isChecked = notificationsEnabled
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Notifications enabled" else "Notifications disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun applyTheme(isDark: Boolean) {
        if (isDark) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
