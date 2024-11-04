package com.vahidmohtasham.worddrag.api.responses

data class StartNewStageRequest(val userId: String, val categoryId: String, val wordsPerStage: Int = 5)
data class MarkWordLearnedRequest(val userId: String, val stageId: String, val wordId: String)
data class CompleteStageRequest(val userId: String, val stageId: String)
data class UserProgressResponse(
    val currentLevel: String,       // سطح فعلی کاربر
    val totalLearnedWords: Int,     // تعداد لغات یادگرفته شده
    val currentLevelLabel: String,     // تعداد لغات یادگرفته شده
    val nextLevel: String?,          // سطح بعدی (می‌تواند null باشد اگر کاربر در بالاترین سطح باشد)
    val requiredWords: Int?          // تعداد لغات مورد نیاز برای رسیدن به سطح بعدی (می‌تواند null باشد)
)


