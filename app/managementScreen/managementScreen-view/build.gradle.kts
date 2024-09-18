plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.usermainscreen_view"
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
    implementation(libs.androidx.compose.ui.graphics)     // Jetpack Compose Graphics
    implementation(libs.androidx.compose.ui.text)         // Jetpack Compose Text
    implementation(libs.androidx.compose.foundation)      // Jetpack Compose Foundation
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.android)
    implementation(project(":app:userMainScreen:userMainScreen-data"))
    implementation(project(":app:userMainScreen:userMainScreen-viewModel"))
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":app:managementScreen:managementScreen-viewModel"))
    implementation(project(":app:managementScreen:managementScreen-data"))
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.android)
    testImplementation(libs.testng)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}