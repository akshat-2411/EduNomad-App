package com.example.edunomad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryCoursesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_courses)

        val categoryName = intent.getStringExtra("categoryName") ?: ""

        val courses = listOf(
            Course(
                title = "Python Basics",
                imageUrl = "",
                imageResId = R.drawable.img_9,
                description = "Learn the fundamentals of Python programming.",
                duration = "6 weeks",
                level = "Beginner",
                instructorName = "John Doe",
                instructorImage = R.drawable.img_15,
                syllabus = listOf("Introduction", "Variables & Data Types", "Functions", "OOP Basics"),
                rating = 4.5f,
                courseVideoUrl = "https://www.example.com/python-course",
                category = "Python"
            ),
            Course(
                title = "Data Science",
                imageUrl = "",
                imageResId = R.drawable.img_10,
                description = "Master data science concepts and apply them in real-world projects.",
                duration = "12 weeks",
                level = "Intermediate",
                instructorName = "Jane Smith",
                instructorImage = R.drawable.img_16,
                syllabus = listOf("Statistics", "Data Preprocessing", "Machine Learning", "Deep Learning"),
                rating = 4.7f,
                courseVideoUrl = "https://www.example.com/datascience-course",
                category = "Data Science"
            ),
            Course(
                title = "Android Development",
                imageUrl = "",
                imageResId = R.drawable.img_11,
                description = "Build your first Android app using Kotlin and XML.",
                duration = "8 weeks",
                level = "Intermediate",
                instructorName = "Michael Brown",
                instructorImage = R.drawable.img_15,
                syllabus = listOf("Introduction to Android", "UI Design", "Networking", "Database Integration"),
                rating = 4.6f,
                courseVideoUrl = "https://www.example.com/android-course",
                category = "Android"
            ),
            Course(
                title = "Machine Learning",
                imageUrl = "",
                imageResId = R.drawable.img_12,
                description = "Get hands-on experience with ML algorithms and AI concepts.",
                duration = "10 weeks",
                level = "Advanced",
                instructorName = "Emily Davis",
                instructorImage = R.drawable.img_16,
                syllabus = listOf("Supervised Learning", "Unsupervised Learning", "Neural Networks", "Deployment"),
                rating = 4.8f,
                courseVideoUrl = "https://www.example.com/ml-course",
                category = "Machine Learning"
            )
        )

        val filteredCourses = courses.filter {
            it.category.equals(categoryName, ignoreCase = true)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CourseAdapter(filteredCourses) { selectedCourse ->
            val intent = Intent(this, CourseDetailsActivity::class.java)
            intent.putExtra("courseTitle", selectedCourse.title)
            // Add other extras as needed
            startActivity(intent)
        }
    }
}
