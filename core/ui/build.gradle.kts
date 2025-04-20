plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.library.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.namle197.ui"
}

dependencies {
    api(projects.core.designsystem)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.coil.kt.compose)
}