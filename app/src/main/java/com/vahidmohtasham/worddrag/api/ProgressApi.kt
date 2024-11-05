package com.vahidmohtasham.worddrag.api

import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.responses.UserProgressResponse
import com.vahidmohtasham.worddrag.screen.category.StageResponse
import com.vahidmohtasham.worddrag.screen.category.UserStagesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProgressApi : ApiService {

    @POST("start-new-stage")
    fun startNewStage(@Body request: StartNewStageRequest): Call<StageResponse>

    @POST("mark-word-learned")
    fun markWordLearned(@Body request: MarkWordLearnedRequest): Call<StageResponse>

    @POST("complete-stage")
    fun completeStage(@Body request: CompleteStageRequest): Call<StageResponse>

    @GET("user-stages")
    fun getUserStages(
        @Query("categoryId") categoryId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Call<UserStagesResponse>

    // اضافه کردن متد جدید برای دریافت پیشرفت کاربر
    @GET("user-progress")
    fun getUserProgress(
    ): Call<UserProgressResponse>
}
