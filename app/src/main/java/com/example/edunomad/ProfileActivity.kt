package com.example.edunomad

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var profileImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        nameText = findViewById(R.id.name)
        emailText = findViewById(R.id.email)
        profileImage = findViewById(R.id.profile_image)

        // --- Stats Section ---
        val coursesEnrolledLayout = findViewById<MaterialCardView>(R.id.courses_enrolled)
        coursesEnrolledLayout.findViewById<TextView>(R.id.stat_number).text = "5"
        coursesEnrolledLayout.findViewById<TextView>(R.id.stat_label).text = "Enrolled"

        val coursesCompletedLayout = findViewById<MaterialCardView>(R.id.courses_completed)
        coursesCompletedLayout.findViewById<TextView>(R.id.stat_number).text = "2"
        coursesCompletedLayout.findViewById<TextView>(R.id.stat_label).text = "Completed"

        val badgesEarnedLayout = findViewById<MaterialCardView>(R.id.badges_earned)
        badgesEarnedLayout.findViewById<TextView>(R.id.stat_number).text = "3"
        badgesEarnedLayout.findViewById<TextView>(R.id.stat_label).text = "Badges"

        // --- Button Actions ---
        findViewById<TextView>(R.id.btn_edit_profile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        findViewById<TextView>(R.id.btn_change_password).setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        findViewById<TextView>(R.id.btn_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Load user data
        loadUserInfo()
    }

    override fun onResume() {
        super.onResume()
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            nameText.text = user.displayName ?: "User"
            emailText.text = user.email ?: "No email available"

            if (user.photoUrl != null) {
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.img_15)
                    .into(profileImage)
            } else {
                profileImage.setImageResource(R.drawable.img_15)
            }
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
