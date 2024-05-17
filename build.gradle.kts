// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) version "8.2.1" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) version "1.9.22" apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) version "1.9.22" apply false
    alias(libs.plugins.com.android.library) version "8.2.1" apply false
    alias(libs.plugins.hilt) version "2.49" apply false
    alias(libs.plugins.ksp) version "1.9.23-1.0.19" apply false
}