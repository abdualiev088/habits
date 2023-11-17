
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.habittracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habittracker"
        minSdk = 32
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
    buildFeatures{
        viewBinding = true
    }

    dataBinding {
        enable = true
    }

//    java {
//        toolchain {
//            languageVersion.set(JavaLanguageVersion.of(8))
//        }
//    }
    kapt {
        useBuildCache = true
    }

}


dependencies {


//    implementation("com.android.tools.build:gradle:7.3.0")


//    Swipe reveal
//    implementation("com.chauthai.swipereveallayout:swipe-reveal-layout:1.4.1")

    implementation("com.airbnb.android:lottie:6.1.0")

    val nav_version = "2.7.4"

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

//    Chart
    implementation("com.github.Gruzer:simple-gauge-android:0.3.1")

//    Architecture dependencies

    val lifecycleVersion = "2.3.1"
    val coroutines = "1.5.0"
    val roomVersion = "2.6.0"

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    // Kotlin components
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")

    // Room components
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
