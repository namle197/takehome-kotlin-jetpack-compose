plugins {
    id("mobiletakehome.android.feature")
    id("mobiletakehome.android.library.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.namle197.mobiletakehome.feature.home"
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)
}