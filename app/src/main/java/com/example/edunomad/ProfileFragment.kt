package com.example.edunomad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Simulated data (normally from DB or Firebase)
        val name = view.findViewById<TextView>(R.id.name)
        val email = view.findViewById<TextView>(R.id.email)
        val profileImage = view.findViewById<ImageView>(R.id.profile_image)

        name.text = "John Doe"
        email.text = "john.doe@example.com"
        profileImage.setImageResource(R.drawable.img_15)

        // Stats (placeholder)
        view.findViewById<TextView>(R.id.courses_enrolled).findViewById<TextView>(R.id.stat_number).text = "5"
        view.findViewById<TextView>(R.id.courses_enrolled).findViewById<TextView>(R.id.stat_label).text = "Enrolled"

        view.findViewById<TextView>(R.id.courses_completed).findViewById<TextView>(R.id.stat_number).text = "2"
        view.findViewById<TextView>(R.id.courses_completed).findViewById<TextView>(R.id.stat_label).text = "Completed"

        view.findViewById<TextView>(R.id.badges_earned).findViewById<TextView>(R.id.stat_number).text = "3"
        view.findViewById<TextView>(R.id.badges_earned).findViewById<TextView>(R.id.stat_label).text = "Badges"

        // Actions
        view.findViewById<TextView>(R.id.btn_edit_profile).setOnClickListener {
            Toast.makeText(activity, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.btn_change_password).setOnClickListener {
            Toast.makeText(activity, "Change Password Clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.btn_logout).setOnClickListener {
            Toast.makeText(activity, "Logged Out", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
