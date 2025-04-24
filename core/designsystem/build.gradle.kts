plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.library.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.namele197.mobiletakehome.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.material3)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(projects.core.testing)

    debugApi(libs.androidx.ui.tooling)

    implementation(libs.androidx.core.ktx)
    implementation(libs.coil.kt.compose)
}