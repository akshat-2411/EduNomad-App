package com.example.edunomad

data class Course(
    val title: String = "",
    val imageUrl: String = "",
    val imageResId: Int = 0,
    val description: String = "",
    val duration: String = "",
    val level: String = "",
    val instructorName: String = "",
    val instructorImage: Int = 0,
    val syllabus: List<String> = emptyList(),
    val rating: Float = 0f,
    val courseVideoUrl: String = "",
    val category: String = ""
)
