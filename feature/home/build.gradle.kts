plugins {
    id("mobiletakehome.android.feature")
    id("mobiletakehome.android.library.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.namle197.mobiletakehome.feature.home"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)

    debugImplementation(libs.androidx.ui.test.manifest)
}