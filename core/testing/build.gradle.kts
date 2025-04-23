plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.hilt")
}

android {
    namespace = "com.namle197.testing"
}

dependencies {
    api(libs.kotlinx.coroutines.test)
    api(projects.core.common)
    api(projects.core.data)
    api(projects.core.model)
    api(projects.core.network)


    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
}