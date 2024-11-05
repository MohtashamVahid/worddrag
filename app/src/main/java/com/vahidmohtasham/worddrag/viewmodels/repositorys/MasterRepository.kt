package com.vahidmohtasham.worddrag.viewmodels.repositorys

import android.content.Context
import com.vahidmohtasham.worddrag.api.ApiService
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.RetrofitInstance
import com.vahidmohtasham.worddrag.utils.SharedPreferencesManager

open class MasterRepository(context: Context) {
    val sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager.init(context)
    private var _progressApi: ProgressApi? = null
    private var _apiService: ApiService? = null


    fun getJwtToken(): String? {
        return sharedPreferencesManager.getJwtToken()
    }

    fun saveJwtToken(token: String?) {
        sharedPreferencesManager.saveJwtToken(token ?: "")
    }

    // متد برای دریافت ApiService
    fun getApiService(context: Context): ApiService {
        if (_apiService == null) {
            updateApiServices(context)
        }
        return _apiService!!
    }

    fun updateApiServices(context: Context) {
        _progressApi = RetrofitInstance.getProgressApi(context)
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