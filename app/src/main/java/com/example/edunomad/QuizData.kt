package com.example.edunomad

object QuizData {
    fun getQuizzes(): List<QuizModel> {
        return listOf(
            QuizModel(
                id = "1",
                title = "Android Basics",
                subtitle = "Test your fundamentals",
                time = "10",
                questionList = listOf(
                    QuestionModel(
                        question = "What is Android?",
                        options = listOf("OS", "App", "Game", "Browser"),
                        correct = "OS"
                    ),
                    QuestionModel(
                        question = "Main language for Android dev?",
                        options = listOf("Swift", "Kotlin", "C++", "JavaScript"),
                        correct = "Kotlin"
                    ),
                    QuestionModel(
                        question = "What is an Activity?",
                        options = listOf("Service", "UI Screen", "Intent", "Toast"),
                        correct = "UI Screen"
                    ),
                    QuestionModel(
                        question = "What is the latest Android version in 2024?",
                        options = listOf("Android 10", "Android 12", "Android 13", "Android 14"),
                        correct = "Android 14"
                    ),
                    QuestionModel(
                        question = "What file is the AndroidManifest?",
                        options = listOf("Manifest.java", "Main.xml", "AndroidManifest.xml", "AppConfig.kt"),
                        correct = "AndroidManifest.xml"
                    )
                )
            ),
            QuizModel(
                id = "2",
                title = "Kotlin Language",
                subtitle = "Test Kotlin knowledge",
                time = "8",
                questionList = listOf(
                    QuestionModel(
                        question = "Kotlin is developed by?",
                        options = listOf("Google", "Microsoft", "JetBrains", "Facebook"),
                        correct = "JetBrains"
                    ),
                    QuestionModel(
                        question = "Is Kotlin statically typed?",
                        options = listOf("Yes", "No", "Sometimes", "Only in Android"),
                        correct = "Yes"
                    ),
                    QuestionModel(
                        question = "Which keyword is used to declare a variable?",
                        options = listOf("val/var", "let", "new", "varies"),
                        correct = "val/var"
                    )
                )
            ),
            QuizModel(
                id = "3",
                title = "Python",
                subtitle = "Basics of Python",
                time = "7",
                questionList = listOf(
                    QuestionModel(
                        question = "Who developed Python?",
                        options = listOf("Guido van Rossum", "Dennis Ritchie", "James Gosling", "Mark Zuckerberg"),
                        correct = "Guido van Rossum"
                    ),
                    QuestionModel(
                        question = "Which of these is a Python data type?",
                        options = listOf("int", "float", "list", "All of the above"),
                        correct = "All of the above"
                    ),
                    QuestionModel(
                        question = "What is the output of: print(2 ** 3)?",
                        options = listOf("6", "8", "9", "5"),
                        correct = "8"
                    )
                )
            ),
            QuizModel(
                id = "4",
                title = "Data Science",
                subtitle = "Fundamentals of Data Science",
                time = "9",
                questionList = listOf(
                    QuestionModel(
                        question = "Which library is used for data manipulation in Python?",
                        options = listOf("NumPy", "Pandas", "Matplotlib", "Seaborn"),
                        correct = "Pandas"
                    ),
                    QuestionModel(
                        question = "Which method is used to handle missing data?",
                        options = listOf("fillna()", "dropna()", "Both", "None"),
                        correct = "Both"
                    ),
                    QuestionModel(
                        question = "Which one is a data visualization library?",
                        options = listOf("TensorFlow", "Seaborn", "Scikit-learn", "NLTK"),
                        correct = "Seaborn"
                    )
                )
            ),
            QuizModel(
                id = "5",
                title = "Machine Learning",
                subtitle = "Intro to ML concepts",
                time = "10",
                questionList = listOf(
                    QuestionModel(
                        question = "Which is a supervised learning algorithm?",
                        options = listOf("K-Means", "Linear Regression", "PCA", "Apriori"),
                        correct = "Linear Regression"
                    ),
                    QuestionModel(
                        question = "What does overfitting mean?",
                        options = listOf("Model performs well on training data but poorly on test data", "Model performs better with more data", "Model has high bias", "Model doesn't learn"),
                        correct = "Model performs well on training data but poorly on test data"
                    ),
                    QuestionModel(
                        question = "Which library is used for ML in Python?",
                        options = listOf("TensorFlow", "Django", "Flask", "BeautifulSoup"),
                        correct = "TensorFlow"
                    )
                )
            ),
            QuizModel(
                id = "6",
                title = "Web Development",
                subtitle = "Front-end and Back-end basics",
                time = "8",
                questionList = listOf(
                    QuestionModel(
                        question = "Which is a front-end language?",
                        options = listOf("HTML", "Node.js", "MongoDB", "Flask"),
                        correct = "HTML"
                    ),
                    QuestionModel(
                        question = "What does CSS stand for?",
                        options = listOf("Computer Style Sheets", "Creative Style System", "Cascading Style Sheets", "Colorful Style Sheets"),
                        correct = "Cascading Style Sheets"
                    ),
                    QuestionModel(
                        question = "Which of these is a back-end framework?",
                        options = listOf("React", "Angular", "Flask", "Bootstrap"),
                        correct = "Flask"
                    )
                )
            )
        )
    }
}
