package com.vahidmohtasham.worddrag.api.entity

data class AppConfig(
    val androidVersion: Int = 1,
    val forceUpdate: Boolean = false,
    val mainUrl: String = "",// آدرس اصلی API
    val serverTime: Long? = 0,
)
