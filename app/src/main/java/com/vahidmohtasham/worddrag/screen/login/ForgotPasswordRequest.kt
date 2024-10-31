package com.vahidmohtasham.worddrag.screen.login

data class ForgotPasswordRequest(
    val email: String
)

data class EmailVerificationRequest(
    val email: String,
    val verificationCode: String
)


data class EmailResendRequest(
    val email: String
)
