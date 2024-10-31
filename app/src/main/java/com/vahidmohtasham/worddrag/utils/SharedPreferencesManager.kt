package com.vahidmohtasham.worddrag.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val PREF_NAME = "samanta_pref"
    private var sharedPreferences: SharedPreferences? = null
    private var encryptedPreferencesHelper: EncryptedPreferencesHelper? = null

    //    val json = Json { ignoreUnknownKeys = true }
    fun init(context: Context): SharedPreferencesManager {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
        if (encryptedPreferencesHelper == null) {
            encryptedPreferencesHelper = EncryptedPreferencesHelper(context)
        }
        return this
    }

    fun saveEncryptedString(key: String, value: String) {
        encryptedPreferencesHelper?.putString(key, value)
    }

    fun getEncryptedString(key: String, defaultValue: String): String {
        return encryptedPreferencesHelper?.getString(key, defaultValue) ?: defaultValue
    }






//    fun putConfig(config: AppConfig) {
//        try {
//            val configJson = json.encodeToString(config)
//            saveString(Constant.APP_CONFIG, configJson)
//        } catch (e: Exception) {
//        }
//    }

    // بازیابی اطلاعات کاربر
//    fun getAppConfig(): AppConfig? {
//        val configJson = getString(Constant.APP_CONFIG, "") ?: return null
//        return try {
//            val config = json.decodeFromString<AppConfig>(configJson)
//            config
//        } catch (e: Exception) {
//            null
//        }
//    }


    // ذخیره رشته
    fun saveString(key: String, value: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    // خواندن رشته با مقدار پیش‌فرض
    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences?.getString(key, defaultValue) ?: defaultValue
    }

    // ذخیره عدد صحیح
    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferences?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    // خواندن عدد صحیح با مقدار پیش‌فرض
    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences?.getInt(key, defaultValue) ?: defaultValue
    }

    // ذخیره عدد صحیح بلند
    fun putLong(key: String, value: Long) {
        val editor = sharedPreferences?.edit()
        editor?.putLong(key, value)
        editor?.apply()
    }

    fun putDouble(key: String, value: Double) {
        val editor = sharedPreferences?.edit()
        editor?.putLong(key, java.lang.Double.doubleToRawLongBits(value)) // تبدیل Double به Long
        editor?.apply()
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        val longBits = sharedPreferences?.getLong(key, java.lang.Double.doubleToRawLongBits(defaultValue)) ?: return defaultValue
        return java.lang.Double.longBitsToDouble(longBits) // تبدیل Long به Double
    }


    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences?.getLong(key, defaultValue) ?: defaultValue
    }

    fun putEncryptedLong(key: String, value: Long) {
        encryptedPreferencesHelper?.putLong(key, value)
    }

    fun getEncryptedLong(key: String, defaultValue: Long): Long {
        return encryptedPreferencesHelper?.getLong(key, defaultValue) ?: defaultValue
    }

    // ذخیره عدد اعشاری
    fun saveFloat(key: String, value: Float) {
        val editor = sharedPreferences?.edit()
        editor?.putFloat(key, value)
        editor?.apply()
    }

    // خواندن عدد اعشاری با مقدار پیش‌فرض
    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences?.getFloat(key, defaultValue) ?: defaultValue
    }

    // ذخیره مقدار بولین
    fun putEncryptedBoolean(key: String, value: Boolean) {
        encryptedPreferencesHelper?.putBoolean(key, value)
    }

    // خواندن مقدار بولین با مقدار پیش‌فرض
    fun getEncryptedBoolean(key: String, defaultValue: Boolean): Boolean {
        return encryptedPreferencesHelper?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    // خواندن مقدار بولین با مقدار پیش‌فرض
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun remove(key: String) {
        val editor = sharedPreferences?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    fun saveJwtToken(token: String) {
        saveString(Constant.JWT_TOKEN, token)
    }

    fun getJwtToken(): String? {
        return getString(Constant.JWT_TOKEN, "")
    }




     fun clear() {
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    fun saveLastEmailVerificationRequestTime(time: Long) {
        sharedPreferences?.edit()?.putLong("email_verification_last_request_time", time)?.apply()
    }

    fun getLastEmailVerificationRequestTime(): Long {
        return sharedPreferences?.getLong("email_verification_last_request_time", 0) ?: 0
    }

    fun saveEmailVerified(isVerified: Boolean) {
        sharedPreferences?.edit()?.putBoolean("email_verified", isVerified)?.apply()
    }
}
