plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.register"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Compose Abhängigkeiten
    implementation(libs.androidx.compose.ui.graphics)     // Jetpack Compose Graphics
    implementation(libs.androidx.compose.ui.text)         // Jetpack Compose Text
    implementation(libs.androidx.compose.foundation)      // Jetpack Compose Foundation
    implementation(libs.androidx.lifecycle.viewmodel.compose)  // ViewModel für Compose
    implementation(libs.androidx.navigation.runtime.ktx)  // Navigation Runtime
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
}