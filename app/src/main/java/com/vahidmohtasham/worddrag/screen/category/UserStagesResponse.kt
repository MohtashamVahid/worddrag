package com.vahidmohtasham.worddrag.screen.category

import com.vahidmohtasham.worddrag.api.BaseResponse

data class UserStagesResponse(
    val stages: List<StageData>, // لیستی از مراحل کاربر
    val page: Int, // شماره صفحه فعلی
    val limit: Int, // تعداد مراحل در هر صفحه
    val totalStages: Int // تعداد کل مراحل
) : BaseResponse()
