package com.vahidmohtasham.worddrag.screen.category

import com.google.gson.annotations.SerializedName

data class StageResponse(
    val message: String?, // پیام وضعیت
    val stage: StageData? // داده‌های مربوط به مرحله
)

data class StageData(
    @SerializedName("_id") val id: String,
    val user: String, // شناسه کاربر
    val category: String, // شناسه دسته‌بندی
    val level: Int, // سطح مرحله
    val words: List<WordData>, // لیستی از کلمات در این مرحله
    val completed: Boolean, // وضعیت تکمیل مرحله
    val lastReviewed: String, // تاریخ آخرین بازبینی مرحله
    val createdAt: String, // تاریخ ایجاد مرحله
    val updatedAt: String // تاریخ آخرین به‌روزرسانی مرحله
)

data class WordData(
    val wordId: WordDetails, // به جای String، یک کلاس برای جزئیات کلمه
    val learned: Boolean, // وضعیت یادگیری
)

data class WordDetails(
    val _id: String, // شناسه کلمه
    val word: String, // متن کلمه
    val meaning: String, // معنی کلمه
    val example: String? // مثال کاربردی
)
