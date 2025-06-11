import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")

    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("com.google.firebase.crashlytics")
}

val mapkitApiKey:String = loadMapkitApiKey()

fun loadMapkitApiKey(): String {
    val properties = Properties()
    val localPropertiesFile = File(project.rootProject.file("local.properties").toString())

    localPropertiesFile.inputStream().use { stream ->
        properties.load(stream)
    }

    return properties.getProperty("MAPKIT_API_KEY", "")
}

android {
    namespace = "com.android.practise.wonderfulwander"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.android.practise.wonderfulwander"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Core
    implementation(project(path = ":core:navigation"))
    implementation(project(path = ":core:presentation"))
    implementation(project(path = ":core:base"))
    implementation(project(path = ":core:network"))
    implementation(project(path = ":core:domain"))
    implementation(project(path = ":core:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    //Firebase BOM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)


    implementation("com.google.android.gms:play-services-auth:20.4.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    //viewmodel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation ("androidx.navigation:navigation-compose:2.5.3")

    //Coil
    implementation ("io.coil-kt:coil-compose:2.4.0")


    //google font
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.8")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.56.1")
    ksp("com.google.dagger:hilt-android-compiler:2.56.1")

    implementation(libs.androidx.hilt.navigation.compose)

    //Yandex Map
    implementation(libs.maps.mobile)
}
