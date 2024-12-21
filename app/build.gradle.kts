plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
//    kotlin("kapt")
    id ("kotlin-kapt")
//    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android") version "2.51" apply false
//    id("com.google.dagger.hilt.android")
//    kotlin("jvm") version "1.9.10" // or the latest stable release


}



android {
    namespace = "com.dicoding.submission1funda"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dicoding.submission1funda"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }

//    hilt {
//        enableAggregatingTask = true
//    }
}

dependencies {
    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit for Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Glide for Image Loading
    implementation(libs.glide)

    // Room Database
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)

    // ViewModel and LiveData (Version 2.6.2)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v262)
    implementation(libs.androidx.lifecycle.livedata.ktx.v262)

    // DataStore Preferences
    implementation(libs.datastore.preferences)

    // Coroutines for Asynchronous Programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Async HTTP Requests
    implementation(libs.android.async.http)

    // WorkManager for background tasks (if needed)
    implementation(libs.androidx.work.runtime)

    // Kotlin Standard Library (if explicitly needed)
    implementation(libs.kotlin.stdlib)
//    kapt(libs.androidx.room.compiler.v250)
}
