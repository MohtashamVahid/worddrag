package com.vahidmohtasham.worddrag.api

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val uniqueCode: String, val email: String, val password: String, val firstName: String, val lastName: String)
data class VerificationRequest(val verificationCode: String)
