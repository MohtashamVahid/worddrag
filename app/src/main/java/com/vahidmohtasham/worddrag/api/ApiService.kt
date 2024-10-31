package com.vahidmohtasham.worddrag.api

import com.vahidmohtasham.worddrag.screen.login.EmailResendRequest
import com.vahidmohtasham.worddrag.screen.login.ForgotPasswordRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("categories")
    suspend fun getCategories(
    ): CategoriesResponse


    @POST("login")
    suspend fun login(@Body loginRequest: Map<String, String>): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: Map<String, String>): RegisterResponse

    @POST("verifyEmail")
    suspend fun verifyEmail(@Body verifyRequest: Map<String, String>): BaseResponse

    @POST("user/login-guest")
    suspend fun loginGuest(@Body request: LoginGuestRequest): LoginResponse

    @POST("user/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ForgotPasswordRequest): BaseResponse

    @POST("user/resend-verification-email")
    suspend fun resendVerificationEmail(@Body request: EmailResendRequest): BaseResponse

    @POST("user/login-email")
    suspend fun loginWithEmail(@Body loginRequest: LoginRequest): LoginResponse

}
