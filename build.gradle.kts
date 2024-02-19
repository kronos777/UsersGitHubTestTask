// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // Check that you have the following line (if not, add it):
        google()  // Google's Maven repository
        mavenCentral()
    }
    dependencies {

        // Add this line
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
}