plugins {
    id("com.android.application") version "8.6.0" apply false // ✅ Match libs.versions.toml
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false // 🆕 Add this line
}
