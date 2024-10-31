package com.vahidmohtasham.worddrag.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _loginResponse = MutableLiveData<User?>()
    val loginResponse: LiveData<User?> get() = _loginResponse

    private val _registrationResponse = MutableLiveData<RegisterResponse?>()
    val registrationResponse: LiveData<RegisterResponse?> get() = _registrationResponse

    private val _verificationResponse = MutableLiveData<String?>()
    val verificationResponse: LiveData<String?> get() = _verificationResponse

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error


    private val _timeRemaining = MutableLiveData<Int>()
    val timeRemaining: LiveData<Int> = _timeRemaining

    private val _verifyEmailResponse = MutableLiveData<String?>()
    val verifyEmailResponse: LiveData<String?> get() = _verifyEmailResponse

    private val timerLimitInSeconds = 120  // دو دقیقه



    private val _resetPasswordResponse = MutableLiveData<String>()
    val resetPasswordResponse: LiveData<String> get() = _resetPasswordResponse



    fun loginGuest(uniqueCode: String) {
        _isLoading.postValue(true)

        viewModelScope.launch {
            try {
                _loginResponse.value = userRepository.loginGuest(uniqueCode)
            }catch (_:Exception){

            } finally {
                _isLoading.postValue(false)

            }
        }
    }

    fun fetchCategories() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val result = userRepository.getCategories()
                _categories.value = result.categories
            } catch (e: Exception) {
                _categories.value = emptyList()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(email, password)
            _loginResponse.value = result
        }
    }

    fun register(uniqueCode: String, email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            val result = userRepository.register(uniqueCode, email, password, firstName, lastName)
            _registrationResponse.value = result
        }
    }

    fun verifyEmail(userId: String, verificationCode: String) {
        viewModelScope.launch {
            val result = userRepository.verifyEmail(userId, verificationCode)
            _verificationResponse.value = result
        }
    }

    fun getUserId(): String? {
        return userRepository.getUserId()
    }

    fun getUniqueID(): String? {
        return userRepository.getUniqueID()
    }

    fun isTokenExpired(context: Context): Boolean {
        return userRepository.isTokenExpired(context)
    }

    fun resendVerificationEmail(email: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                _error.postValue(null)
                val response = userRepository.resendVerificationEmail(email)
                userRepository.saveLastEmailVerificationRequestTime(System.currentTimeMillis())

                startTimer(timerLimitInSeconds)
                _error.postValue(response.getMessageOrError())

            } catch (e: Exception) {
                _error.postValue("خطا در ارسال ایمیل.")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun startTimer(seconds: Int) {
        viewModelScope.launch {
            for (i in seconds downTo 0) {
                _timeRemaining.postValue(i)
                delay(1000)
            }
        }
    }
    fun resetPassword(email: String) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _error.value = null
                val response = userRepository.resetPassword(email)
                _resetPasswordResponse.postValue(response.getMessageOrError())
            } catch (e: Exception) {
                _error.value = "خطا در بازیابی رمز عبور"
            } finally {
                _isLoading.postValue(false)

            }
        }
    }

    fun loginWithEmail(identifier: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _error.value = null

                val response = userRepository.loginWithEmail(identifier, password)
                userRepository.saveJwtToken(response.token)
                 _loginResponse.postValue(response.user)

            } catch (e: HttpException) {
                // بررسی کد HTTP و پیام خطا از سرور
                val errorResponse = e.response()?.errorBody()?.string()
                val errorMessage = JSONObject(errorResponse).getString("message")  // استخراج پیام خطا از JSON
                _error.value = errorMessage  // نمایش پیام دقیق خطا
            } catch (e: Exception) {
                _error.value = "خطای ناشناخته رخ داد"
            } finally {
                _isLoading.postValue(false)
            }
        }
    }


    fun clearError() {
        _error.postValue(null)
    }

}
