plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.tallytitans"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tallytitans"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.ui.graphics)     // Jetpack Compose Graphics
    implementation(libs.androidx.compose.ui.text)         // Jetpack Compose Text
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.navigation.compose) // Jetpack Compose Foundation
    implementation(project(":app:login:login-view"))
    implementation(project(":app:login:login-viewmodel"))
    implementation(project(":app:login:login-data"))
    implementation(project(":app:register:register-view"))
    implementation(project(":app:register:register-viewmodel"))
    implementation(project(":app:register:register-data"))
    implementation(project(":app:userMainScreen:userMainScreen-viewModel"))
    implementation(project(":app:userMainScreen:userMainScreen-data"))
    implementation(project(":app:userMainScreen:userMainScreen-view"))
    implementation(project(":app:gameScreen:gameScreen-view"))
    implementation(project(":app:gameScreen:gameScreen-viewModel"))
    implementation(project(":app:gameScreen:gameScreen-data"))
    implementation(project(":app:adminMainScreen:adminMainScreen-view"))
    implementation(project(":app:adminMainScreen:adminMainScreen-viewModel"))
    implementation(project(":app:adminMainScreen:adminMainScreen-data"))

    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)}