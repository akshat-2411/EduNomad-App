package com.example.edunomad

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        // Initialize Toolbar, Drawer, and NavigationView
        // Toolbar, Drawer, Navigation
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_notification -> {
                    Toast.makeText(this, "Notifications Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_courses -> {
                    startActivity(Intent(this, MainActivity3::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, AskMeAnythingActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_notifications -> {
                    Toast.makeText(this, "Notification clicked", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        // Bottom Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottome_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> true
                R.id.nav_course -> {
                    startActivity(Intent(this, MainActivity3::class.java))
                    true
                }
                R.id.nav_askmeanything -> {
                    startActivity(Intent(this, AskMeAnythingActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    try {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                else -> false
            }
        }

        // Search bar click -> go to SearchActivity
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // Course List
        val courses = listOf(
            Course(
                title = "Python Basics",
                imageResId = R.drawable.img_9,
                description = "Learn the fundamentals of Python programming.",
                duration = "6 weeks",
                level = "Beginner",
                instructorName = "John Doe",
                instructorImage = R.drawable.img_15,
                syllabus = listOf("Introduction", "Variables & Data Types", "Functions", "OOP Basics"),
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
                syllabus = listOf("Statistics", "Data Preprocessing", "Machine Learning", "Deep Learning"),
                rating = 4.7f,
                courseVideoUrl = "https://www.example.com/datascience-course"
            ),
            Course(
                title = "Android Development",
                imageResId = R.drawable.img_11,
                description = "Build your first Android app using Kotlin and XML.",
                duration = "8 weeks",
                level = "Intermediate",
                instructorName = "Michael Brown",
                instructorImage = R.drawable.img_15,
                syllabus = listOf("Introduction to Android", "UI Design", "Networking", "Database Integration"),
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
                syllabus = listOf("Supervised Learning", "Unsupervised Learning", "Neural Networks", "Deployment"),
                rating = 4.8f,
                courseVideoUrl = "https://www.example.com/ml-course"
            )
        )


        val courseRecyclerView = findViewById<RecyclerView>(R.id.courseRecyclerView)
        courseRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val courseAdapter = CourseAdapter(courses) { selectedCourse ->
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
        courseRecyclerView.adapter = courseAdapter

        // Search filtering
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                courseAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // See All Courses
        val seeAllCourses = findViewById<TextView>(R.id.seeAllCourse)
        seeAllCourses.setOnClickListener {
            startActivity(Intent(this, AllCoursesActivity::class.java))
        }

        // Category List
        val categories = listOf(
            Category("Business", R.drawable.img_7),
            Category("Technology", R.drawable.img_8),
            Category("Marketing", R.drawable.img_6) ,
            Category("Coding", R.drawable.img_5),
            Category("AI", R.drawable.img_3),
            Category("Developement", R.drawable.img_4)
        )

        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        categoryRecyclerView.adapter = CategoryAdapter(categories) { selectedCategory ->
            val intent = Intent(this, CategoryCoursesActivity::class.java)
            intent.putExtra("categoryName", selectedCategory.name)
            startActivity(intent)
        }



        // See All Categories
        val seeAllCategories = findViewById<TextView>(R.id.seeAllCategories)
        seeAllCategories.setOnClickListener {
            startActivity(Intent(this, AllCategoriesActivity::class.java))
        }

        // Banner Image Slider
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        val bannerImages = listOf(R.drawable.img_9, R.drawable.img_10, R.drawable.img_11)
        viewPager.adapter = ImageSliderAdapter(bannerImages)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        autoScrollImages(bannerImages.size)
    }

    private fun autoScrollImages(imageCount: Int) {
        runnable = object : Runnable {
            override fun run() {
                val nextItem = (viewPager.currentItem + 1) % imageCount
                viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        val menuItem = menu?.findItem(R.id.action_notification)
        val badge = BadgeDrawable.create(this)
        badge.number = 3
        badge.isVisible = true
        menuItem?.actionView?.let { actionView ->
            BadgeUtils.attachBadgeDrawable(badge, actionView)
        }

        return true
    }





}
