package com.vahidmohtasham.worddrag.api

data class User(
    val id: String,
    val uniqueCode: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val emailVerified: Boolean = false
)

data class LoginResponse(
    val user: User?,
    val token: String?
) : BaseResponse()


data class LoginGuestRequest(val uniqueCode: String)

