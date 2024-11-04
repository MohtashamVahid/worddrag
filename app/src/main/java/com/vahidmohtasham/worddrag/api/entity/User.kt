package com.vahidmohtasham.worddrag.api.entity

import com.vahidmohtasham.worddrag.api.responses.BaseResponse

data class User(
    val _id: String,
    val uniqueCode: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val isGuest: Boolean?,
    val emailVerified: Boolean = false
)

data class LoginResponse(
    val user: User?,
    val token: String?
) : BaseResponse()

data class RegisterResponse(
    val user: User?,
 ) : BaseResponse()


data class LoginGuestRequest(val uniqueCode: String)

