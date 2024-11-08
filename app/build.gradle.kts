plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}


android {
    namespace = "com.vahidmohtasham.worddrag"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vahidmohtasham.worddrag"
        minSdk = 24
        targetSdk = 34
        versionCode = 5
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BANNER_AD_ID_PROFILE", "\"7fa22e57-68c4-4157-85a0-485f7fa08f25\"")
        buildConfigField("String", "BANNER_AD_ID_LEARNING", "\"3dd18455-d03e-4eef-8a98-8135be876d4c\"")
        buildConfigField("String", "BANNER_AD_ID_Interstitial", "\"6b229fa5-9195-4f72-af76-58c3c0476589\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8)) // یا 11 اگر بخواهید از Java 11 استفاده کنید
        }
    }

    applicationVariants.all {

        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl

            val variantName = name
            val versionName = versionName
            val versionCode = versionCode
            val buildType = buildType.name

            outputImpl.outputFileName = "${variantName}-bazar-v${versionName}-${versionCode}.apk"
        }

    }



    applicationVariants.all {

        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl

            val variantName = name
            val versionName = versionName
            val versionCode = versionCode
            val buildType = buildType.name

            outputImpl.outputFileName = "${variantName}-bazar-v${versionName}-${versionCode}.apk"
        }

    }



}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.security.crypto)
    implementation(libs.java.jwt)
    implementation(libs.logging.interceptor)

    implementation(libs.coil.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.sdk)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
