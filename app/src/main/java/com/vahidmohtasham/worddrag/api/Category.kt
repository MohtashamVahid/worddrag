package com.vahidmohtasham.worddrag.api

data class Category(
    val id: String,                 // معادل _id در JSON
    val name: String,               // نام دسته
    val description: String?,       // توضیحات
    val icon: String?,              // آیکون
    val parentCategory: String?,     // شناسه دسته والد
    val isActive: Boolean,          // وضعیت فعال
    val createdAt: String,          // زمان ایجاد
    val updatedAt: String           // زمان آخرین بروزرسانی
)


data class CategoriesResponse(
    val categories: List<Category>
):BaseResponse()
