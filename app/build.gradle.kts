import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

val appName = "Flash Hideline"
val feature = "Show & Filter Hidelines"

android {
    namespace = "com.fdhasna21.flashhideline"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fdhasna21.flashhideline"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY") ?: ""}\"")
    }

    signingConfigs {
        create("release") {
            val storeFilePath = localProperties.getProperty("KEYSTORE_FILE")
            if (!storeFilePath.isNullOrEmpty()) {
                storeFile = file(storeFilePath)
                storePassword = localProperties.getProperty("KEYSTORE_PASSWORD")
                keyAlias = localProperties.getProperty("KEY_ALIAS")
                keyPassword = localProperties.getProperty("KEY_PASSWORD")
            }
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            dimension = "version"
            versionCode = 1000
            resValue("string", "app_name", "$appName-DEV")
        }
        create("prod") {
            dimension = "version"
            versionCode = 3
            resValue("string", "app_name", appName)
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    variantFilter {
        val flavorName = flavors.firstOrNull()?.name
        val buildTypeName = buildType.name
        if ((flavorName == "dev" && buildTypeName == "release") ||
            (flavorName == "prod" && buildTypeName == "debug")
        ) {
            ignore = true
        }
    }

    applicationVariants.all {
        val variant = this
        outputs.all {
            val output = this as? com.android.build.gradle.api.ApkVariantOutput
            if (output != null) {
                val formattedVariantName = variant.name.replaceFirstChar { it.uppercase() }
                output.outputFileName = "$appName v${variant.versionName} - $feature $formattedVariantName.apk"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

tasks.whenTaskAdded {
    if (name.startsWith("package") && name.endsWith("Bundle")) {
        val variantName = name.removePrefix("package").removeSuffix("Bundle")
        val formattedVariant = variantName.replaceFirstChar { it.uppercase() }

        tasks.findByName("sign${variantName}Bundle")?.doLast {
            val bundleDir = file("${layout.buildDirectory.get()}/outputs/bundle/${variantName.replaceFirstChar { it.lowercase() }}")
            val defaultBundle = file("$bundleDir/app-${variantName.replaceFirstChar { it.lowercase() }}.aab")

            if (defaultBundle.exists()) {
                val newAabName = "$appName v${android.defaultConfig.versionName} - $feature $formattedVariant.aab"
                defaultBundle.renameTo(file("$bundleDir/$newAabName"))
            }
        }
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.webkit)

    // Jetpack Compose (BOM)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.coil.compose)

    // Dependency Injection (Hilt)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Network (Retrofit & OkHttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.jackson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.databind)

    // Local Storage (Room)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}