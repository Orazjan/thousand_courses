plugins {
    alias(libs.plugins.kotlin.android)
    id("com.android.library")
}

android {
    namespace = "com.atnzvdev.thousand.domain"
    compileSdk = 36

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

    kotlin {
        jvmToolchain(17)
    }



dependencies {
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}