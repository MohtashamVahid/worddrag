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

-keep class kotlinx.coroutines.** { *; }
-keep class androidx.security.** { *; }
-keep class androidx.lifecycle.** { *; }


# Keep Coil's classes
-keep class coil.** { *; }
-keep class coil.compose.** { *; }

# Keep Accompanist's classes
-keep class com.google.accompanist.** { *; }

# Keep classes for JWT
-keep class com.auth0.** { *; }
-keep class com.auth0.jwt.** { *; }
-dontwarn com.auth0.jwt.**

-keepattributes InnerClasses

-keep class io.jsonwebtoken.** { *; }
-keepnames class io.jsonwebtoken.* { *; }
-keepnames interface io.jsonwebtoken.* { *; }

-keep class org.bouncycastle.** { *; }
-keepnames class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

# Jackson classes (for JWT)
-keep class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**


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
-keep class com.vahidmohtasham.worddrag.viewmodels.** { *; }



# ProGuard rules for unit tests
-keep class androidx.test.** { *; }


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.chartboost.sdk.Chartboost$CBPIDataUseConsent
-dontwarn com.chartboost.sdk.Chartboost
-dontwarn com.chartboost.sdk.ChartboostDelegate
-dontwarn com.chartboost.sdk.Libraries.CBLogging$Level
-dontwarn com.chartboost.sdk.Model.CBError$CBImpressionError
-dontwarn com.google.android.gms.ads.MediaContent
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient
-dontwarn com.google.android.gms.ads.nativead.MediaView
-dontwarn com.google.android.gms.ads.nativead.NativeAd$Image
-dontwarn com.google.android.gms.ads.nativead.NativeAd
-dontwarn com.google.android.gms.ads.nativead.NativeAdView
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn com.google.errorprone.annotations.CheckReturnValue
-dontwarn com.google.errorprone.annotations.Immutable
-dontwarn com.google.errorprone.annotations.RestrictedApi
-dontwarn com.huawei.hms.ads.identifier.AdvertisingIdClient$Info
-dontwarn com.huawei.hms.ads.identifier.AdvertisingIdClient
-dontwarn com.ironsource.mediationsdk.IronSource
-dontwarn com.ironsource.mediationsdk.logger.IronSourceError
-dontwarn com.ironsource.mediationsdk.logger.IronSourceLogger$IronSourceTag
-dontwarn com.ironsource.mediationsdk.logger.LogListener
-dontwarn com.ironsource.mediationsdk.model.Placement
-dontwarn com.ironsource.mediationsdk.sdk.InitializationListener
-dontwarn com.ironsource.mediationsdk.sdk.InterstitialListener
-dontwarn com.ironsource.mediationsdk.sdk.RewardedVideoManualListener
-dontwarn com.mbridge.msdk.newinterstitial.out.MBNewInterstitialHandler
-dontwarn com.mbridge.msdk.newinterstitial.out.NewInterstitialListener
-dontwarn com.mbridge.msdk.out.MBRewardVideoHandler
-dontwarn com.mbridge.msdk.out.MBridgeIds
-dontwarn com.mbridge.msdk.out.MBridgeSDKFactory
-dontwarn com.mbridge.msdk.out.RewardInfo
-dontwarn com.mbridge.msdk.out.RewardVideoListener
-dontwarn com.mbridge.msdk.out.SDKInitStatusListener
-dontwarn com.mbridge.msdk.system.a
-dontwarn com.mbridge.msdk.video.bt.module.b.g
-dontwarn com.startapp.sdk.adsbase.Ad
-dontwarn com.startapp.sdk.adsbase.StartAppAd$AdMode
-dontwarn com.startapp.sdk.adsbase.StartAppAd
-dontwarn com.startapp.sdk.adsbase.StartAppSDK
-dontwarn com.startapp.sdk.adsbase.adlisteners.AdDisplayListener
-dontwarn com.startapp.sdk.adsbase.adlisteners.AdEventListener
-dontwarn com.startapp.sdk.adsbase.adlisteners.VideoListener
-dontwarn com.startapp.sdk.adsbase.model.AdPreferences
-dontwarn com.unity3d.ads.IUnityAdsInitializationListener
-dontwarn com.unity3d.ads.IUnityAdsLoadListener
-dontwarn com.unity3d.ads.IUnityAdsShowListener
-dontwarn com.unity3d.ads.UnityAds$UnityAdsInitializationError
-dontwarn com.unity3d.ads.UnityAds$UnityAdsLoadError
-dontwarn com.unity3d.ads.UnityAds$UnityAdsShowCompletionState
-dontwarn com.unity3d.ads.UnityAds$UnityAdsShowError
-dontwarn com.unity3d.ads.UnityAds
-dontwarn com.unity3d.ads.metadata.MetaData
-dontwarn com.unity3d.services.banners.BannerErrorInfo
-dontwarn com.unity3d.services.banners.BannerView$IListener
-dontwarn com.unity3d.services.banners.BannerView
-dontwarn com.unity3d.services.banners.UnityBannerSize
-dontwarn ir.apptimize.R$id
-dontwarn ir.apptimize.R$layout
