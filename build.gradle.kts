// Top-level build file where you can add configuration options common to all sub-projects/modules.
extra["compileSdk"] = 35
extra["minSdk"] = 26
extra["targetSdk"] = 35

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    alias(libs.plugins.compose.compiler) apply false
}