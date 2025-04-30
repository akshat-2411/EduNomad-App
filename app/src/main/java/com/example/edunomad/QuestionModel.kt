// --- QuestionModel.kt ---
package com.example.edunomad

import java.io.Serializable

data class QuestionModel(
    val question: String,
    val options: List<String>,
    val correct: String
) : Serializable