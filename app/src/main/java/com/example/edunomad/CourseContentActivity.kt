package com.example.edunomad

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CourseContentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: CourseContentAdapter
    private val topicList = mutableListOf<CourseTopic>()

    private lateinit var userId: String
    private lateinit var courseTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_content)

        recyclerView = findViewById(R.id.courseContentRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        userId = intent.getStringExtra("userId") ?: "userId"
        courseTitle = intent.getStringExtra("courseTitle") ?: ""

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CourseContentAdapter(topicList)
        recyclerView.adapter = adapter

        if (courseTitle.isNotEmpty()) {
            checkEnrollmentAndFetchContent()
        } else {
            Toast.makeText(this, "Course info missing!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun checkEnrollmentAndFetchContent() {
        val courseRef = FirebaseDatabase.getInstance().reference
            .child("enrollments")
            .child(userId)
            .child(courseTitle)

        progressBar.visibility = View.VISIBLE

        courseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.hasChild("syllabus")) {
                    fetchCourseContent()
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@CourseContentActivity, "You are not enrolled in this course.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@CourseContentActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchCourseContent() {
        val syllabusRef = FirebaseDatabase.getInstance().reference
            .child("enrollments")
            .child(userId)
            .child(courseTitle)
            .child("syllabus")

        syllabusRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                topicList.clear()
                for (topicSnapshot in snapshot.children) {
                    val topic = topicSnapshot.getValue(CourseTopic::class.java)
                    topic?.let { topicList.add(it) }
                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@CourseContentActivity, "Failed to load content", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
