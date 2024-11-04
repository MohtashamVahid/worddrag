package com.vahidmohtasham.worddrag.api.responses

import com.google.gson.annotations.SerializedName

data class AppConfigResponse(
    @SerializedName("androidVersion") val androidVersion: Int = 1,
    @SerializedName("forceUpdate") val forceUpdate: Boolean = false,
    @SerializedName("mainUrl") val mainUrl: String = "",
    @SerializedName("serverTime") val serverTime: Long = 0L,
)
