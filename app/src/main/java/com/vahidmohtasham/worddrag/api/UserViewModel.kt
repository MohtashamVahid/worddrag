package com.vahidmohtasham.worddrag.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {


    private val _loginResponse = MutableLiveData<User?>()
    val loginResponse: LiveData<User?> get() = _loginResponse

    private val _registrationResponse = MutableLiveData<LoginResponse?>()
    val registrationResponse: LiveData<LoginResponse?> get() = _registrationResponse

    private val _verificationResponse = MutableLiveData<LoginResponse?>()
    val verificationResponse: LiveData<LoginResponse?> get() = _verificationResponse

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loginGuest(uniqueCode: String) {
        _isLoading.postValue(true)

        viewModelScope.launch {
            try {
                _loginResponse.value = userRepository.loginGuest(uniqueCode)
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
            _registrationResponse.value = result.getOrNull()
        }
    }

    fun verifyEmail(userId: String, verificationCode: String) {
        viewModelScope.launch {
            val result = userRepository.verifyEmail(userId, verificationCode)
            _verificationResponse.value = result.getOrNull()
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
}
