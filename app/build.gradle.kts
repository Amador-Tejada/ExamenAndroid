plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs") // Para Safe Args en Navigation Component
}

android {
    namespace = "com.example.examen"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.examen"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // ===== DEPENDENCIAS PARA FUNCIONALIDADES ESPECÍFICAS =====

    // Picasso: librería para cargar y cachear imágenes desde URLs
    // Facilita mostrar imágenes de Internet de forma eficiente
    implementation("com.squareup.picasso:picasso:2.8")

    // Retrofit: cliente HTTP para hacer llamadas a APIs REST de forma sencilla
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Conversor Gson para Retrofit: transforma JSON en objetos Kotlin automáticamente
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Corrutinas de Kotlin para Android: permiten ejecutar código asíncrono
    // Esencial para no bloquear el hilo principal con operaciones de red
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    
    // AÑADID ESTAS TRES LÍNEAS para el Navigation Drawer
    // Estas librerías permiten navegar entre fragmentos y conectar
    // el Navigation Component con el Drawer
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.drawerlayout)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}