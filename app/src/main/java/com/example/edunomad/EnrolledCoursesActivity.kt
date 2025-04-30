package com.example.edunomad

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EnrolledCoursesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var enrolledAdapter: CourseAdapter
    private val enrolledCourses = mutableListOf<Course>()
    private lateinit var database: DatabaseReference
    private lateinit var userId: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enrolled_course)

        // Get userId from intent instead of FirebaseAuth
        userId = intent.getStringExtra("userId") ?: "userId"

        recyclerView = findViewById(R.id.enrolledC)
        recyclerView.layoutManager = LinearLayoutManager(this)

        enrolledAdapter = CourseAdapter(enrolledCourses) { course ->
            // TODO: Open course details if needed
        }
        recyclerView.adapter = enrolledAdapter

        database = FirebaseDatabase.getInstance().getReference("EnrolledCourses").child(userId)
        fetchEnrolledCourses()
    }

    private fun fetchEnrolledCourses() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                enrolledCourses.clear()
                for (courseSnapshot in snapshot.children) {
                    val course = courseSnapshot.getValue(Course::class.java)
                    if (course != null) {
                        enrolledCourses.add(course)
                    }
                }
                enrolledAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EnrolledCoursesActivity, "Failed to load courses", Toast.LENGTH_SHORT).show()
                Log.e("FirebaseError", error.message)
            }
        })
    }
}
