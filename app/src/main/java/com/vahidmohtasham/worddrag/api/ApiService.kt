package com.vahidmohtasham.worddrag.api

import com.vahidmohtasham.worddrag.api.entity.CategoriesResponse
import com.vahidmohtasham.worddrag.api.entity.LoginGuestRequest
import com.vahidmohtasham.worddrag.api.entity.LoginResponse
import com.vahidmohtasham.worddrag.api.entity.RegisterResponse
import com.vahidmohtasham.worddrag.api.responses.AppConfigResponse
import com.vahidmohtasham.worddrag.api.responses.BaseResponse
import com.vahidmohtasham.worddrag.api.responses.LoginRequest
import com.vahidmohtasham.worddrag.screen.login.EmailResendRequest
import com.vahidmohtasham.worddrag.screen.login.ForgotPasswordRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("categories")
    suspend fun getCategories(
    ): CategoriesResponse

    @GET("config/app-config")
    fun getAppConfig(): Call<AppConfigResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: Map<String, String>): LoginResponse

    @POST("user/register")
    suspend fun register(@Body registerRequest: Map<String, String>): RegisterResponse

    @POST("user/verify-email")
    suspend fun verifyEmail(@Body verifyRequest: Map<String, String>): BaseResponse

    @POST("user/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ForgotPasswordRequest): BaseResponse

    @POST("user/resend-verification-email")
    suspend fun resendVerificationEmail(): BaseResponse

    @POST("user/login-email")
    suspend fun loginWithEmail(@Body loginRequest: LoginRequest): LoginResponse

}
