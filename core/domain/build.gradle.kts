plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.hilt")
}

android {
    namespace = "com.namle197.domain"

    /*testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }*/
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

    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.network)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(projects.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}