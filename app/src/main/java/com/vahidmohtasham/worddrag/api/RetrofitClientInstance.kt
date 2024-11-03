package com.vahidmohtasham.worddrag.api

import android.content.Context
import android.os.Build
import android.util.Log

import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.utils.Constant
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object RetrofitInstance {
    private var baseUrl: String? = ""

    // Logging Interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
    }

    // Get SSLSocketFactory for custom certificates
    private fun getSSLSocketFactory(context: Context): SSLSocketFactory {
        val cf = CertificateFactory.getInstance("X.509")
        val certs = listOf(R.raw.ca, R.raw.fullchain)
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
        }

        // Load certificates from resources
        certs.forEachIndexed { index, certResId ->
            context.resources.openRawResource(certResId).use { caInput ->
                val ca = cf.generateCertificate(caInput)
                keyStore.setCertificateEntry("cert_$index", ca)
            }
        }

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        return sslContext.socketFactory
    }

    // Get TrustManager for SSL
    private fun getTrustManager(): X509TrustManager {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers = trustManagerFactory.trustManagers
        return trustManagers[0] as X509TrustManager
    }

    // Build Retrofit instance
    private fun getRetrofit(context: Context, token: String = ""): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(token))
//            .addInterceptor(TokenInterceptor(context))
//            .addInterceptor(VersionInterceptor())
//            .addInterceptor(NetworkConnectionInterceptor(context))

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                clientBuilder.sslSocketFactory(getSSLSocketFactory(context), getTrustManager())
                    .hostnameVerifier { hostname, session ->
                        HttpsURLConnection.getDefaultHostnameVerifier().verify(hostname, session)
                    }
            }
        } catch (e: Exception) {
            Log.e("RetrofitInstance", "SSL setup failed", e)
        }

        val client = clientBuilder.build()

        // Set the base URL from SharedPreferences or use a default value
        baseUrl = SharedPreferencesManager.init(context)
            .getString(Constant.MAIN_URL, "https://worddrag.vahidmohtasham.com:3003/api/")
        if (baseUrl.isNullOrEmpty()) {
            baseUrl = "https://worddrag.vahidmohtasham.com:3003/api/"
        }

        return try {
            Retrofit.Builder()
                .baseUrl(baseUrl!!)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            Log.e("RetrofitInstance", "Retrofit build failed, using default URL", e)
            Retrofit.Builder()
                .baseUrl("https://worddrag.vahidmohtasham.com:3003/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    // Public function to get ApiService
    fun getApiService(context: Context): ApiService {
        val token = SharedPreferencesManager.init(context).getJwtToken()
        return getRetrofit(context, token ?: "").create(ApiService::class.java)
    }
    fun getProgressApi(context: Context): ProgressApi {
        val token = SharedPreferencesManager.init(context).getJwtToken()
        return getRetrofit(context, token ?: "").create(ProgressApi::class.java)
    }
}
