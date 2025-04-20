plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.hilt")
}

android {
    namespace = "com.namle197.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}