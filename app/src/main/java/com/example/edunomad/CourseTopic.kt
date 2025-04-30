package com.example.edunomad
data class CourseTopic(
    val title: String = "",
    val videoUrl: String = "",
    val pdfUrl: String = "",
    var isCompleted: Boolean = false // default false
)
