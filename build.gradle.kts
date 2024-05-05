buildscript {
    val kotlin_version by extra("2.0.0-Beta4")
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
    repositories {
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

//buildscript {
//    dependencies{
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
//
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
//        classpath("com.google.gms:google-services:4.4.0")
//    }
//}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}