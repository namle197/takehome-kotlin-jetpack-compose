plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.hilt")
    id("mobiletakehome.android.room")
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.database"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(libs.androidx.junit)
    api(libs.androidx.test.core.ktx)
    api(libs.androidx.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.androidx.ui.test.junit4)
    api(libs.kotlinx.coroutines.test)

    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)

    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.serialization.json)
    androidTestImplementation(projects.core.testing)

    debugImplementation(libs.androidx.ui.test.manifest)
}