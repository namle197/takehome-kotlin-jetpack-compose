plugins {
    //alias(libs.plugins.android.application)
    //alias(libs.plugins.kotlin.android)
    id("mobiletakehome.android.application") // Use this from build-logic instead of 2 above

    // alias(libs.plugins.kotlin.compose)
    id("mobiletakehome.android.application.compose") // Use this from build-logic instead of the one above

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlinx-serialization")
}

android {
    namespace = "com.namle197.mobiletakehome"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.namle197.mobiletakehome"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:userdetail"))

    api(projects.core.network)
    api(projects.core.model)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}