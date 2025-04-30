package com.example.edunomad

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MyCoursesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myCoursesAdapter: MyCoursesAdapter
    private val courseList = mutableListOf<Course>()

    // Dummy user ID; ideally this should come from Intent like in previous examples
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_courses)

        // Get userId from intent (fallback to a default one)
        userId = intent.getStringExtra("userId") ?: "userId"

        recyclerView = findViewById(R.id.myCoursesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        myCoursesAdapter = MyCoursesAdapter(courseList)
        recyclerView.adapter = myCoursesAdapter

        fetchUserCourses()
    }

    private fun fetchUserCourses() {
        val ref = FirebaseDatabase.getInstance().reference
            .child("enrollments")
            .child(userId)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                for (courseSnap in snapshot.children) {
                    val course = courseSnap.getValue(Course::class.java)
                    course?.let { courseList.add(it) }
                }
                myCoursesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyCoursesActivity, "Failed to load courses", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
