plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.library")
}

android{
    namespace = "com.atnzvdev.thousand.domain"
    compileSdk {
        version = release(36)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}



dependencies {
    implementation(libs.material)
    testImplementation(libs.junit)
}