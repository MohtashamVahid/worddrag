package com.vahidmohtasham.worddrag.api

import android.content.Context
import android.util.Log
import com.auth0.jwt.JWT
import com.vahidmohtasham.worddrag.screen.login.EmailResendRequest
import com.vahidmohtasham.worddrag.screen.login.ForgotPasswordRequest
import com.vahidmohtasham.worddrag.utils.Constant
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager
import java.util.UUID

class UserRepository(private val context: Context, private val apiService: ApiService) {

    private val sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager.init(context)

    private var userId: String? = null

    private var uniqueCode: String? = null

    init {
        uniqueCode = sharedPreferencesManager.getString(Constant.UNIQUE_CODE, null)
        if (uniqueCode == null) {
            uniqueCode = UUID.randomUUID().toString()
            sharedPreferencesManager.saveString(Constant.UNIQUE_CODE, uniqueCode!!)
        }
    }


    suspend fun getCategories(): CategoriesResponse {
        return apiService.getCategories()
    }

    suspend fun loginGuest(uniqueCode: String): User? {
        try {
            val response = apiService.loginGuest(LoginGuestRequest(uniqueCode))
            response.user?.let {
                sharedPreferencesManager.saveString(Constant.USER_ID, it._id)
            }
            response.token?.let {
                sharedPreferencesManager.saveJwtToken(it)
            }
            return response.user
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun login(email: String, password: String): User? {
        try {
            val response = apiService.login(mapOf("email" to email, "password" to password))
            return response.user
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun register(uniqueCode: String, email: String, password: String, firstName: String, lastName: String): RegisterResponse? {
        try {
            val response = apiService.register(
                mapOf(
                    "uniqueCode" to uniqueCode,
                    "email" to email,
                    "password" to password,
                    "firstName" to firstName,
                    "lastName" to lastName
                )
            )
            return response
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun verifyEmail(userId: String, verificationCode: String): String? {
        try {
            val response = apiService.verifyEmail(mapOf("userId" to userId, "verificationCode" to verificationCode))
            return response.getMessageOrError()
        } catch (e: Exception) {
            return null
        }
    }

    fun getUserId(): String? {
        return if (userId != null)
            userId
        else {
            userId = sharedPreferencesManager.getString(Constant.USER_ID, null)
            userId
        }
    }


    fun isTokenExpired(context: Context): Boolean {
        try {
            val token = SharedPreferencesManager.init(context).getJwtToken()
            val serverTime = currentServerTimeMillis(context)
            if (token.isNullOrEmpty()) {
                Log.d("TokenStatus", "Token is empty")
                return true
            }
            val expiresAt = JWT.decode(token).claims["exp"]?.asLong()
            val expirationTime = (expiresAt ?: 0L) * 1000
            Log.d("TokenStatus", "Expiration Time: $expirationTime, Server Time: $serverTime")
            return expirationTime < serverTime
        } catch (e: Exception) {
            Log.e("TokenStatus", "Error checking token expiration", e)
            return true
        }
    }

    fun currentServerTimeMillis(context: Context): Long {
        val timeOffset = SharedPreferencesManager.init(context).getLong(Constant.TIME_OFFSET_KEY, 0L)
        return System.currentTimeMillis() + timeOffset
    }

    fun getUniqueID(): String? {
        return sharedPreferencesManager.getString(Constant.UNIQUE_CODE, null)
    }

    suspend fun resetPassword(email: String): BaseResponse {
        val resetPasswordRequest = ForgotPasswordRequest(email)
        return RetrofitInstance.getApiService(context).resetPassword(resetPasswordRequest)
    }

    suspend fun resendVerificationEmail(email: String): BaseResponse {
        return RetrofitInstance.getApiService(context).resendVerificationEmail(EmailResendRequest(email))
    }

    fun saveLastEmailVerificationRequestTime(time: Long) {
        sharedPreferencesManager.putLong("email_verification_last_request_time", time)
    }


    fun getLastEmailVerificationRequestTime(): Long {
        return sharedPreferencesManager.getLong("email_verification_last_request_time", 0)
    }

    suspend fun loginWithEmail(identifier: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(identifier, password)
        return RetrofitInstance.getApiService(context).loginWithEmail(loginRequest)
    }

    fun saveJwtToken(token: String?) {
        sharedPreferencesManager.saveJwtToken(token ?: "")
    }
}
