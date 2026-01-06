import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)


    alias(libs.plugins.pluginSerialization)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}


val geoCoderApiKey:String = loadGeoCoderApiKey()

fun loadGeoCoderApiKey(): String {
    // Always check environment variable first (for CI/CD)
    val envApiKey = System.getenv("GEO_CODER_API_KEY")
    if (envApiKey != null && envApiKey.isNotBlank()) {
        println("Using GEO_CODER_API_KEY from environment variable")
        return envApiKey
    }

    // Try local.properties file for local development
    val localPropertiesFile = File(project.rootProject.file("local.properties").toString())
    if (localPropertiesFile.exists()) {
        try {
            val properties = Properties()
            localPropertiesFile.inputStream().use { stream ->
                properties.load(stream)
            }
            val fileApiKey = properties.getProperty("GEO_CODER_API_KEY", "")
            if (fileApiKey.isNotBlank()) {
                println("Using GEO_CODER_API_KEY from local.properties")
                return fileApiKey
            }
        } catch (e: Exception) {
            println("Warning: Could not read local.properties: ${e.message}")
        }
    } else {
        println("local.properties not found. Make sure to set GEO_CODER_API_KEY environment variable in CI/CD.")
    }

    // Return empty string as fallback (build will likely fail, but gracefully)
    println("Warning: GEO_CODER_API_KEY not found in environment or local.properties")
    return ""
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
        buildConfigField("String","GEO_CODER_API_KEY", value = "\"$geoCoderApiKey\"")
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