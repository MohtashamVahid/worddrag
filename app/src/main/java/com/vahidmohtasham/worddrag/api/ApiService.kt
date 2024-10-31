package com.vahidmohtasham.worddrag.api

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
    suspend fun register(@Body registerRequest: Map<String, String>): Response<LoginResponse>

    @POST("verifyEmail")
    suspend fun verifyEmail(@Body verifyRequest: Map<String, String>): Response<LoginResponse>

    @POST("user/loginGuest")
    suspend fun loginGuest(@Body request: LoginGuestRequest): LoginResponse

}
