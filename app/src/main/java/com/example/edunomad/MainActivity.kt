package com.example.edunomad

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        // Toolbar, Drawer, NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.navigation_view)
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val prefs = getSharedPreferences("app_settings", MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("dark_theme", false)
        if(isDarkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

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

        // --- Navigation Header: Show Firebase User Info ---
        setupNavHeader()

        // Navigation Drawer Item Clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> drawerLayout.closeDrawer(GravityCompat.START).run { true }
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
                    startActivity(Intent(this, SettingsActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
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
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Search bar click -> SearchActivity
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // --- Banner Image Slider ---
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        val bannerImages = listOf(R.drawable.img_9, R.drawable.img_10, R.drawable.img_11)
        viewPager.adapter = ImageSliderAdapter(bannerImages)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        autoScrollImages(bannerImages.size)

        // --- Categories and Courses ---
        setupCategories()
        setupCourses()

        // See All Categories
        findViewById<TextView>(R.id.seeAllCategories).setOnClickListener {
            startActivity(Intent(this, AllCategoriesActivity::class.java))
        }

        // See All Courses
        findViewById<TextView>(R.id.seeAllCourse).setOnClickListener {
            startActivity(Intent(this, AllCoursesActivity::class.java))
        }
    }

    // Auto scroll images
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

    override fun onResume() {
        super.onResume()
        setupNavHeader() // refresh user info
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

    // --- Function to setup Nav Header ---
    private fun setupNavHeader() {
        val headerView = navView.getHeaderView(0)
        val userImage = headerView.findViewById<ShapeableImageView>(R.id.userImage)
        val welcomeText = headerView.findViewById<TextView>(R.id.welcomeText)
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            welcomeText.text = "Welcome, ${user.displayName ?: "User"} 👋"
            if (user.photoUrl != null) {
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.img_15)
                    .into(userImage)
            } else {
                userImage.setImageResource(R.drawable.img_15)
            }
        } else {
            welcomeText.text = "Welcome, Guest 👋"
            userImage.setImageResource(R.drawable.img_15)
        }

        // Click header -> open ProfileActivity
        headerView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    // --- Sample setup for Categories ---
    private fun setupCategories() {
        val categories = listOf(
            Category("Business", R.drawable.img_7),
            Category("Technology", R.drawable.img_8),
            Category("Marketing", R.drawable.img_6),
            Category("Coding", R.drawable.img_5),
            Category("AI", R.drawable.img_3),
            Category("Development", R.drawable.img_4)
        )

        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = CategoryAdapter(categories) { selectedCategory ->
            val intent = Intent(this, CategoryCoursesActivity::class.java)
            intent.putExtra("categoryName", selectedCategory.name)
            startActivity(intent)
        }
    }

    // --- Sample setup for Courses ---
    private fun setupCourses() {
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

        // Optional: Search Filtering
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                courseAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
