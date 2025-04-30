package com.example.edunomad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllCoursesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_courses)

        // ✅ Extended List of Courses
        val allCourses = listOf(
            Course(
                title = "Python Basics",
                imageResId = R.drawable.img_9,
                description = "Learn the fundamentals of Python programming.",
                duration = "6 weeks",
                level = "Beginner",
                instructorName = "John Doe",
                instructorImage = R.drawable.img_15,
                syllabus = listOf(
                    "Introduction",
                    "Variables & Data Types",
                    "Functions",
                    "OOP Basics"
                ),
                rating = 4.5f,
                courseVideoUrl = "https://www.example.com/python-course"
            ),
            Course(
                title = "Data Science",
                imageResId = R.drawable.img_10,
                description = "Master data science concepts and apply them in real-world projects.",
                duration = "12 weeks",
                level = "Intermediate",
                instructorName = "Jane Smith",
                instructorImage = R.drawable.img_16,
                syllabus = listOf(
                    "Statistics",
                    "Data Preprocessing",
                    "Machine Learning",
                    "Deep Learning"
                ),
                rating = 4.7f,
                courseVideoUrl = "https://www.example.com/data-science-course"
            ),
            Course(
                title = "Android Development",
                imageResId = R.drawable.img_15,
                description = "Build your first Android app using Kotlin and XML.",
                duration = "8 weeks",
                level = "Intermediate",
                instructorName = "Michael Brown",
                instructorImage = R.drawable.img_16,
                syllabus = listOf(
                    "Introduction to Android",
                    "UI Design",
                    "Networking",
                    "Database Integration"
                ),
                rating = 4.6f,
                courseVideoUrl = "https://www.example.com/android-course"
            ),
            Course(
                title = "Machine Learning",
                imageResId = R.drawable.img_12,
                description = "Get hands-on experience with ML algorithms and AI concepts.",
                duration = "10 weeks",
                level = "Advanced",
                instructorName = "Emily Davis",
                instructorImage = R.drawable.img_16,
                syllabus = listOf(
                    "Supervised Learning",
                    "Unsupervised Learning",
                    "Neural Networks",
                    "Deployment"
                ),
                rating = 4.8f,
                courseVideoUrl = "https://www.example.com/machine-learning-course"
            ),
            Course(
                title = "Web Development",
                imageResId = R.drawable.img_13,
                description = "Learn HTML, CSS, JavaScript, and frameworks like React & Angular.",
                duration = "10 weeks",
                level = "Beginner",
                instructorName = "Daniel Lee",
                instructorImage = R.drawable.img_16,
                syllabus = listOf(
                    "HTML Basics",
                    "CSS Styling",
                    "JavaScript Fundamentals",
                    "React & Angular"
                ),
                rating = 4.4f,
                courseVideoUrl = "https://www.example.com/web-development-course"
            ),
            Course(
                title = "Cyber Security",
                imageResId = R.drawable.img_14,
                description = "Understand cybersecurity principles and protect digital systems.",
                duration = "8 weeks",
                level = "Intermediate",
                instructorName = "Sophia Johnson",
                instructorImage = R.drawable.img_16,
                syllabus = listOf(
                    "Network Security",
                    "Cryptography",
                    "Malware Analysis",
                    "Penetration Testing"
                ),
                rating = 4.6f,
                courseVideoUrl = "https://www.example.com/cyber-security-course"
            )
        )

        // ✅ Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.allCoursesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this@AllCoursesActivity, 2) // Use explicit context
        recyclerView.adapter = CourseAdapter(allCourses) { selectedCourse ->
            val intent = Intent(this, CourseDetailsActivity::class.java)
            intent.putExtra("courseTitle", selectedCourse.title)
            intent.putExtra("courseImage", selectedCourse.imageResId)
            intent.putExtra("courseDescription", selectedCourse.description)
            intent.putExtra("courseDuration", selectedCourse.duration)
            intent.putExtra("courseLevel", selectedCourse.level)
            intent.putExtra("instructorName", selectedCourse.instructorName)
            intent.putExtra("instructorImage", selectedCourse.instructorImage)
            intent.putExtra("syllabus", selectedCourse.syllabus.toTypedArray())
            intent.putExtra("rating", selectedCourse.rating)
            intent.putExtra("courseVideoUrl", selectedCourse.courseVideoUrl)

            startActivity(intent)
        }


    }
}