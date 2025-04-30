
package com.example.edunomad

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class CourseDetailsActivity : AppCompatActivity() {

    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        // Retrieve userId from intent
        userId = intent.getStringExtra("userId") ?: "userId"

        // Other intent extras
        val courseTitle = intent.getStringExtra("courseTitle") ?: "No Title"
        val courseImage = intent.getIntExtra("courseImage", 0)
        val courseDescription = intent.getStringExtra("courseDescription") ?: "No Description"
        val courseDuration = intent.getStringExtra("courseDuration") ?: "N/A"
        val courseLevel = intent.getStringExtra("courseLevel") ?: "N/A"
        val instructorName = intent.getStringExtra("instructorName") ?: "Unknown Instructor"
        val instructorImage = intent.getIntExtra("instructorImage", 0)
        val rating = intent.getFloatExtra("rating", 0f)

        // Set view content
        findViewById<TextView>(R.id.courseTitle).text = courseTitle
        findViewById<ImageView>(R.id.courseImage).setImageResource(courseImage)
        findViewById<TextView>(R.id.courseDescription).text = courseDescription
        findViewById<TextView>(R.id.courseDuration).text = "Duration: $courseDuration"
        findViewById<TextView>(R.id.courseLevel).text = "Difficulty: $courseLevel"
        findViewById<TextView>(R.id.instructorName).text = instructorName
        findViewById<ImageView>(R.id.instructorImage).setImageResource(instructorImage)
        findViewById<RatingBar>(R.id.ratingBar).rating = rating

        val enrollButton = findViewById<Button>(R.id.enrollButton)
        val startCourseButton = findViewById<Button>(R.id.startCourseButton)

        startCourseButton.isEnabled = false

        enrollButton.setOnClickListener {
            enrollInCourse(courseTitle, courseDuration, courseLevel, instructorName, enrollButton, startCourseButton)
        }

        startCourseButton.setOnClickListener {
            Toast.makeText(this, "Starting $courseTitle...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CourseContentActivity::class.java)
            intent.putExtra("courseTitle", courseTitle)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    private fun enrollInCourse(
        courseTitle: String,
        courseDuration: String,
        courseLevel: String,
        instructorName: String,
        enrollButton: Button,
        startCourseButton: Button
    ) {
        val courseRef = FirebaseDatabase.getInstance().reference
            .child("enrollments")
            .child(userId)
            .child(courseTitle)

        val courseMap = mapOf(
            "title" to courseTitle,
            "duration" to courseDuration,
            "level" to courseLevel,
            "instructor" to instructorName
        )

        courseRef.updateChildren(courseMap)
            .addOnSuccessListener {
                startCourseButton.isEnabled = true
                startCourseButton.setBackgroundColor(resources.getColor(R.color.green_color))
                enrollButton.setBackgroundColor(resources.getColor(R.color.green_light))

                Toast.makeText(this, "Enrolled in $courseTitle!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Enrollment failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
