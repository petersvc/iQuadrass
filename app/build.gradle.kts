plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
<<<<<<< HEAD
    id("com.google.gms.google-services") // Para utilizar Firebase/Firestore
    id("kotlin-kapt") // Necessário para o Room
=======
    id("com.google.gms.google-services")
>>>>>>> main
}

android {
    namespace = "com.example.iquadras"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.iquadras"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
<<<<<<< HEAD

=======
>>>>>>> main
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
<<<<<<< HEAD

    buildFeatures {
        compose = true // Habilitar Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Versão do Compose
    }

=======
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
>>>>>>> main
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

<<<<<<< HEAD
    // Firebase/Firestore
=======
    // necessário para o Firebase/Firestore
>>>>>>> main
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.firestore.ktx)

    // REST com Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.core)

<<<<<<< HEAD
    // Room (Banco de Dados Local)
    implementation("androidx.room:room-runtime:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")

    // Jetpack Compose e Material Design
=======
>>>>>>> main
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
<<<<<<< HEAD

    // Navegação com Jetpack Compose
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // GSON para JSON
    implementation(libs.gson)

    // Material Icons e Location Services
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.play.services.location)

    // Permissões e mapas
    implementation(libs.accompanist.permissions.v0320)
    implementation(libs.coil.compose)
    implementation(libs.play.services.maps.v1900)

    // Outras bibliotecas necessárias
=======
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.gson)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.play.services.location)
    implementation(libs.accompanist.permissions.v0320)  // Atualize para a versão mais recente
    implementation(libs.coil.compose) // Verifique se há uma versão mais recente
    implementation(libs.play.services.maps.v1900)



>>>>>>> main
    implementation(libs.androidx.material)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx.v251)

<<<<<<< HEAD
    // Atualização do Compose
=======
    // Atulizando as dependências do Compose
>>>>>>> main
    implementation(libs.androidx.material.v151)
    implementation(libs.androidx.ui.v151)
    implementation(libs.androidx.ui.tooling.preview.v151)
    implementation(libs.androidx.lifecycle.runtime.ktx.v285)

<<<<<<< HEAD
    // Testes unitários e de integração
=======
>>>>>>> main
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
<<<<<<< HEAD

    // Dependências de depuração
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
=======
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
>>>>>>> main
