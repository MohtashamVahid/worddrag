package com.vahidmohtasham.worddrag.api

import android.content.Context
import android.util.Log
import com.auth0.jwt.JWT
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
                sharedPreferencesManager.saveString(Constant.USER_ID, it.id)
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

    suspend fun register(uniqueCode: String, email: String, password: String, firstName: String, lastName: String): Result<LoginResponse> {
        return try {
            val response = apiService.register(
                mapOf(
                    "uniqueCode" to uniqueCode,
                    "email" to email,
                    "password" to password,
                    "firstName" to firstName,
                    "lastName" to lastName
                )
            )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyEmail(userId: String, verificationCode: String): Result<LoginResponse> {
        return try {
            val response = apiService.verifyEmail(mapOf("userId" to userId, "verificationCode" to verificationCode))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Verification failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
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
}
