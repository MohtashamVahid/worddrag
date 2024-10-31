package com.vahidmohtasham.worddrag.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class EncryptedPreferencesHelper(context: Context) {

    private var sharedPreferences: SharedPreferences?=null

    init {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

        } catch (e: Exception) {
            e.printStackTrace()
         }
    }

    fun putString(key: String, value: String?) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences?.getString(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences?.getInt(key, defaultValue)?:defaultValue
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences?.edit()?.putLong(key, value)?.apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences?.getLong(key, defaultValue)?:defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences?.getBoolean(key, defaultValue)?:defaultValue
    }

    fun remove(key: String) {
        sharedPreferences?.edit()?.remove(key)?.apply()
    }

    fun clear() {
        sharedPreferences?.edit()?.clear()?.apply()
    }

    fun saveJwtToken(token: String) {
        putString(Constant.JWT_TOKEN, token)
    }

    fun getJwtToken(): String? {
        return getString(Constant.JWT_TOKEN, "")
    }

    companion object {
        private const val PREFS_NAME = "TouchGramEncryptedPrefs"
    }
}
