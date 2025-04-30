// --- QuizModel.kt ---
package com.example.edunomad

import java.io.Serializable

data class QuizModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val questionList: List<QuestionModel>
) : Serializable