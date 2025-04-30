plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt") // ✅ KAPT plugin for annotation processing
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.edunomad"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.edunomad"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // ✅ Correct viewBinding setup
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Existing dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase Database KTX
    implementation("com.google.firebase:firebase-database-ktx")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // New dependencies for Quiz Activity
    implementation("androidx.recyclerview:recyclerview:1.2.1") // For list handling (if you're using a RecyclerView)
    implementation("com.google.android.material:material:1.10.0") // Material components (for quiz UI)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // ViewModel support
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") // LiveData support for reactive UI
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7") // For navigation components in your Quiz Activity
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7") // For navigation UI helpers

    // Firebase dependencies if needed (for saving quiz results or user data)
 //   implementation("com.google.firebase:firebase-auth:21.1.0") // Firebase Authentication if needed
  //  implementation("com.google.firebase:firebase-firestore:24.2.0") // Firestore for saving quiz data

    // Optional: Testing dependencies if you want to add tests related to quiz functionality
    testImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // If you're using Firebase Storage for quiz media (like images or videos)
  //  implementation("com.google.firebase:firebase-storage-ktx:20.1.0")

    // Other potential dependencies specific to quiz functionality (e.g., graphics or animations)
    implementation("com.github.bumptech.glide:glide:4.15.1") // For handling images if required in your quiz activity
    kapt("com.github.bumptech.glide:compiler:4.15.1") // For Glide annotation processing

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))

    // ✅ Correct with Firebase BOM
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.android.exoplayer:exoplayer:2.15.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1") // For logging
    implementation("androidx.media3:media3-exoplayer:1.0.0-beta01")
    implementation("androidx.media3:media3-ui:1.0.0-beta01")
}
