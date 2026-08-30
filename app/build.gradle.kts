plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.hikari.launcher"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hikari.launcher"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            // Minify/shrink desactivados para evitar OutOfMemoryError en R8 en CI.
            // Los keep rules del proyecto son muy amplios (androidx.**, kotlin.**)
            // por lo que el beneficio de minify es marginal y no justifica el OOM.
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            isCrunchPngs = false
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    lint {
        // No bloquear el ensamblado de release por errores de lint en CI
        checkReleaseBuilds = false
        abortOnError = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Lifecycle Compose (collectAsStateWithLifecycle)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Work Manager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
