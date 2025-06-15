plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id(libs.plugins.secrets.gradle.plugin.get().pluginId)

    alias(libs.plugins.pluginSerialization)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String","GEO_CODER_BASE_URL", value = "\"https://geocode-maps.yandex.ru/\"")
        buildConfigField("String","API_BASE_URL", value = "\"https://wonderful-wander.ru\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    //Core 
    implementation(project(path =":core:base"))

    implementation(libs.bundles.network.deps)

    implementation("com.auth0.android:jwtdecode:2.0.2")

    testImplementation(libs.junit)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

}