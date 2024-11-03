package com.vahidmohtasham.worddrag.api
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequestBuilder = originalRequest.newBuilder()

        if (!authToken.isNullOrEmpty()) {
            newRequestBuilder.header("Authorization", "Bearer $authToken")
        }

        val newRequest = newRequestBuilder.build()
        return chain.proceed(newRequest)
    }
}
