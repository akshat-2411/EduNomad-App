package com.example.edunomad

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        // Simulated data (normally from DB or Firebase)
        val name = findViewById<TextView>(R.id.name)
        val email = findViewById<TextView>(R.id.email)
        val profileImage = findViewById<ImageView>(R.id.profile_image)

        name.text = "John Doe"
        email.text = "john.doe@example.com"
        profileImage.setImageResource(R.drawable.img_15)

        // Stats Section (Accessing TextViews inside included layout)

        // For "Courses Enrolled"
        val coursesEnrolledLayout = findViewById<LinearLayout>(R.id.courses_enrolled)
        val statNumberCourses = coursesEnrolledLayout.findViewById<TextView>(R.id.stat_number)
        val statLabelCourses = coursesEnrolledLayout.findViewById<TextView>(R.id.stat_label)

        statNumberCourses.text = "5"  // Set number of courses enrolled
        statLabelCourses.text = "Enrolled"  // Set the label

        // For "Courses Completed"
        val coursesCompletedLayout = findViewById<LinearLayout>(R.id.courses_completed)
        val statNumberCompleted = coursesCompletedLayout.findViewById<TextView>(R.id.stat_number)
        val statLabelCompleted = coursesCompletedLayout.findViewById<TextView>(R.id.stat_label)

        statNumberCompleted.text = "2"  // Set number of courses completed
        statLabelCompleted.text = "Completed"  // Set the label

        // For "Badges Earned"
        val badgesEarnedLayout = findViewById<LinearLayout>(R.id.badges_earned)
        val statNumberBadges = badgesEarnedLayout.findViewById<TextView>(R.id.stat_number)
        val statLabelBadges = badgesEarnedLayout.findViewById<TextView>(R.id.stat_label)

        statNumberBadges.text = "3"  // Set number of badges earned
        statLabelBadges.text = "Badges"  // Set the label

        // Actions (Buttons for Edit Profile, Change Password, Logout)
        findViewById<TextView>(R.id.btn_edit_profile).setOnClickListener {
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.btn_change_password).setOnClickListener {
            Toast.makeText(this, "Change Password Clicked", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.btn_logout).setOnClickListener {
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        }
    }
}
