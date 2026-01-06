import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id(libs.plugins.secrets.gradle.plugin.get().pluginId)

    alias(libs.plugins.pluginSerialization)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}


val geoCoderApiKey:String = loadMapkitApiKey()

fun loadMapkitApiKey(): String {
    val properties = Properties()
    val localPropertiesFile = File(project.rootProject.file("local.properties").toString())

    localPropertiesFile.inputStream().use { stream ->
        properties.load(stream)
    }

    return properties.getProperty("GEO_CODER_API", "")
}

android {
    namespace = "com.example.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String","GEO_CODER_BASE_URL", value = "\"https://geocode-maps.yandex.ru/\"")
        buildConfigField("String","API_BASE_URL", value = "\"http://localhost:8080/\"")
        buildConfigField("String","GEO_CODER_API", value = "\"$geoCoderApiKey\"")
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