package com.vahidmohtasham.worddrag.screen.category

import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.responses.UserProgressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgressRepository(private val apiService: ProgressApi) {

    fun startNewStage(request: StartNewStageRequest, onResult: (StageResponse?) -> Unit) {
        apiService.startNewStage(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun markWordLearned(request: MarkWordLearnedRequest, onResult: (StageResponse?) -> Unit) {
        apiService.markWordLearned(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun completeStage(request: CompleteStageRequest, onResult: (StageResponse?) -> Unit) {
        apiService.completeStage(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getUserStages(userId: String, categoryId: String, page: Int = 1, limit: Int = 10, onResult: (UserStagesResponse?) -> Unit) {
        apiService.getUserStages(userId, categoryId, page, limit).enqueue(object : Callback<UserStagesResponse> {
            override fun onResponse(call: Call<UserStagesResponse>, response: Response<UserStagesResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<UserStagesResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    // اضافه کردن متد جدید برای دریافت پیشرفت کاربر
    fun getUserProgress(userId: String, onResult: (UserProgressResponse?) -> Unit) {
        apiService.getUserProgress(userId).enqueue(object : Callback<UserProgressResponse> {
            override fun onResponse(call: Call<UserProgressResponse>, response: Response<UserProgressResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<UserProgressResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
