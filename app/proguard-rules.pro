# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-dontwarn okhttp3.**
-dontwarn retrofit2.**


# Preserve all public classes and methods from the androidx libraries
-keep class androidx.** { *; }

# Keep Retrofit's model classes (you may need to customize this based on your models)
-keep class com.vahidmohtasham.worddrag.models.** { *; }

# Keep Gson serialization/deserialization
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }

# Keep Coil's classes
-keep class coil.** { *; }
-keep class coil.compose.** { *; }

# Keep Accompanist's classes
-keep class com.google.accompanist.** { *; }

# Keep classes for JWT
-keep class com.auth0.** { *; }

# Keep Security Crypto
-keep class androidx.security.** { *; }

# Keep all classes in your package
-keep class com.vahidmohtasham.worddrag.** { *; }

# Keep lifecycle and LiveData classes
-keep class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.LiveData { *; }

# Keep any specific classes you don't want to be obfuscated or removed
# For example, if you have a specific class that you want to keep
-keep class com.vahidmohtasham.worddrag.api.** { *; }
-keep class com.vahidmohtasham.worddrag.utils.** { *; }



# ProGuard rules for unit tests
-keep class androidx.test.** { *; }
