package com.vahidmohtasham.worddrag.viewmodels.repositorys

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.vahidmohtasham.worddrag.api.ApiService
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.responses.BaseResponse
import com.vahidmohtasham.worddrag.api.entity.CategoriesResponse
import com.vahidmohtasham.worddrag.api.entity.LoginGuestRequest
import com.vahidmohtasham.worddrag.api.responses.LoginRequest
import com.vahidmohtasham.worddrag.api.entity.LoginResponse
import com.vahidmohtasham.worddrag.api.entity.RegisterResponse
import com.vahidmohtasham.worddrag.api.RetrofitInstance
import com.vahidmohtasham.worddrag.api.entity.AppConfig
import com.vahidmohtasham.worddrag.api.entity.User
import com.vahidmohtasham.worddrag.api.responses.AppConfigResponse
import com.vahidmohtasham.worddrag.screen.login.EmailResendRequest
import com.vahidmohtasham.worddrag.screen.login.ForgotPasswordRequest
import com.vahidmohtasham.worddrag.utils.Constant
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class UserRepository(private val context: Context) {

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

    private var _progressApi: ProgressApi? = null
    private var _apiService: ApiService? = null

    companion object {
        const val ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L // یک روز به میلی‌ثانیه
    }


    suspend fun getCategories(): CategoriesResponse {
        return getApiService(context).getCategories()
    }



    suspend fun login(email: String, password: String): User? {
        try {
            val response = getApiService(context).login(mapOf("email" to email, "password" to password))
            return response.user
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun register(email: String, password: String, firstName: String, lastName: String): RegisterResponse? {
        try {
            val response = getApiService(context).register(
                mapOf(
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
            val response = getApiService(context).verifyEmail(mapOf("userId" to userId, "verificationCode" to verificationCode))
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
        return getApiService(context).resetPassword(resetPasswordRequest)
    }

    suspend fun resendVerificationEmail(): BaseResponse {
        return getApiService(context).resendVerificationEmail()
    }

    fun saveLastEmailVerificationRequestTime(time: Long) {
        sharedPreferencesManager.putLong("email_verification_last_request_time", time)
    }


    fun getLastEmailVerificationRequestTime(): Long {
        return sharedPreferencesManager.getLong("email_verification_last_request_time", 0)
    }

    suspend fun loginWithEmail(identifier: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(identifier, password)
        return getApiService(context).loginWithEmail(loginRequest)
    }

    fun saveJwtToken(token: String?) {
        sharedPreferencesManager.saveJwtToken(token ?: "")
    }

    fun hasFreeTimeRemaining(adId: String): Boolean {
        var savedTime = sharedPreferencesManager.getLong(adId, 0L)


        val currentTime = currentServerTimeMillis()
        Log.d("vhdmht", "" + adId + " hasFreeTimeRemaining " + (currentTime < savedTime))

        return currentTime < savedTime
    }


    fun setRandomTimer(id: String) {
        val randomHours = (2..6).random()  // تولید زمان رندوم بین 2 تا 6 ساعت
        val randomTimeInMillis = randomHours * 60L * 60L * 1000L  // تبدیل ساعت به میلی‌ثانیه

        val expiryTime = currentServerTimeMillis() + randomTimeInMillis  // زمان پایان را محاسبه کن

        sharedPreferencesManager.putLong(id, expiryTime)  // ذخیره زمان پایان
    }

    fun shouldShowUpdateDialog(): Boolean {
        val lastPromptTime = sharedPreferencesManager.getLong(Constant.PREF_LAST_UPDATE_PROMPT, 0)
        val currentTime = currentServerTimeMillis()
        return currentTime - lastPromptTime > ONE_DAY_IN_MILLIS
    }


    fun currentServerTimeMillis(): Long {
        val timeOffset = sharedPreferencesManager.getLong(Constant.TIME_OFFSET_KEY, 0L)
        return System.currentTimeMillis() + timeOffset
    }

    fun loadConfigFromPreferences(context: Context): AppConfig {
        val sharedPreferencesManager = SharedPreferencesManager.init(context)
        val androidVersion = sharedPreferencesManager.getInt(Constant.ANDROID_VERSION_KEY, 1)
        val forceUpdate = sharedPreferencesManager.getBoolean(Constant.FORCE_UPDATE_KEY, false)
        val mainUrl = sharedPreferencesManager.getString(Constant.MAIN_URL, Constant.BASE_URL)

        return AppConfig(
            androidVersion = androidVersion,
            forceUpdate = forceUpdate,
            mainUrl = mainUrl ?: Constant.BASE_URL,
            serverTime = 0,
        )
    }

    fun loadConfig() {
        getApiService(context).getAppConfig().enqueue(object : Callback<AppConfigResponse> {
            override fun onResponse(call: Call<AppConfigResponse>, response: Response<AppConfigResponse>) {
                if (response.isSuccessful) {
                    try {
                        response.body()?.let {
                            val pref = SharedPreferencesManager.init(context)
                            val config = AppConfig(
                                it.androidVersion,
                                it.forceUpdate,
                                it.mainUrl, 0
                            )

                            it.mainUrl?.let { mainUrl ->
                                pref.saveString(Constant.MAIN_URL, mainUrl)
                            }

                            it.serverTime?.let { serverTime ->
                                val deviceTime = System.currentTimeMillis()
                                val timeOffset = serverTime - deviceTime
                                pref.putLong(Constant.TIME_OFFSET_KEY, timeOffset)
                            }
                            saveConfigToPreferences(config)
                        }

                    } catch (e: Exception) {
                    }
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"

                }
            }

            override fun onFailure(call: Call<AppConfigResponse>, t: Throwable) {
            }
        })
    }


    fun saveConfigToPreferences(config: AppConfig) {
        sharedPreferencesManager.saveInt(Constant.ANDROID_VERSION_KEY, config.androidVersion ?: 0)
        sharedPreferencesManager.putBoolean(Constant.FORCE_UPDATE_KEY, config.forceUpdate ?: false)
        sharedPreferencesManager.saveString(Constant.MAIN_URL, config.mainUrl)
    }

    fun savePrefLastUpdatePrompt() {
        sharedPreferencesManager.putLong(
            Constant.PREF_LAST_UPDATE_PROMPT,
            currentServerTimeMillis()
        )
    }

    fun saveEmailVerified(emailVerified: Boolean) {
        sharedPreferencesManager.putBoolean(Constant.EMAIL_VERIFIED, emailVerified)
    }

    fun isEmailVerified(): Boolean {
        return sharedPreferencesManager.getBoolean(Constant.EMAIL_VERIFIED, false)
    }

    fun getJwtToken(): String? {
        return sharedPreferencesManager.getJwtToken()
    }

    // متد برای دریافت ApiService
    fun getApiService(context: Context): ApiService {
        if (_apiService == null) {
            updateApiServices(context)
        }
        return _apiService!!
    }

    fun updateApiServices(context: Context) {
        _progressApi = RetrofitInstance.getProgressApi( context)
        _apiService = RetrofitInstance.getApiService(context)
    }

    // متد برای دریافت ProgressApi
    fun getProgressApi(context: Context): ProgressApi {
        if (_progressApi == null) {
            updateApiServices(context)
        }
        return _progressApi!!
    }

}
