package com.vahidmohtasham.worddrag.api

data class ApiResponse<T>(
    val data: T? = null,
    val error: String? = null
)
