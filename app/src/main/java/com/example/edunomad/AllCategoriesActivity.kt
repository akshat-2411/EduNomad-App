package com.example.edunomad

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllCategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_categories)

        val allCategories = listOf(
            Category("Business", R.drawable.img_7),
            Category("Technology", R.drawable.img_8),
            Category("Digital Marketing", R.drawable.img_6),
            Category("Coding", R.drawable.img_5),
            Category("AI", R.drawable.img_3),
            Category("Developement", R.drawable.img_4)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.allCategoriesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = CategoryAdapter(allCategories) { selectedCategory ->
            // 👇 On click, go to CategoryCoursesActivity and pass the category name
            val intent = Intent(this, CategoryCoursesActivity::class.java)
            intent.putExtra("categoryName", selectedCategory.name)
            startActivity(intent)
        }
    }
}
