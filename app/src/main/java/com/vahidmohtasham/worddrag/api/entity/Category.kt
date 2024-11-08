package com.vahidmohtasham.worddrag.api.entity

import com.google.gson.annotations.SerializedName
import com.vahidmohtasham.worddrag.api.responses.BaseResponse

data class Category(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String?,
    val icon: String?,              // آیکون
    val parentCategory: String?,     // شناسه دسته والد
    val isActive: Boolean,          // وضعیت فعال
    val createdAt: String,          // زمان ایجاد
    val updatedAt: String           // زمان آخرین بروزرسانی
)


data class CategoriesResponse(
    val categories: List<Category>
) : BaseResponse()
