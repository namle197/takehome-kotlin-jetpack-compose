plugins {
    id("mobiletakehome.android.library")
    id("mobiletakehome.android.hilt")
    id("mobiletakehome.android.room")
}

android {
    namespace = "com.google.samples.apps.nowinandroid.core.database"
}

dependencies {

    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}