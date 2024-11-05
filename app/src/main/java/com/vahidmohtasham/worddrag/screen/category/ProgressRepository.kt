package com.vahidmohtasham.worddrag.screen.category

import android.content.Context
import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.responses.UserProgressResponse
import com.vahidmohtasham.worddrag.viewmodels.repositorys.MasterRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgressRepository(val context: Context) : MasterRepository(context) {

    fun startNewStage(request: StartNewStageRequest, onResult: (StageResponse?) -> Unit) {
        getProgressApi(context).startNewStage(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun markWordLearned(request: MarkWordLearnedRequest, onResult: (StageResponse?) -> Unit) {
        getProgressApi(context).markWordLearned(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun completeStage(request: CompleteStageRequest, onResult: (StageResponse?) -> Unit) {
        getProgressApi(context).completeStage(request).enqueue(object : Callback<StageResponse> {
            override fun onResponse(call: Call<StageResponse>, response: Response<StageResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<StageResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getUserStages( categoryId: String, page: Int = 1, limit: Int = 10, onResult: (UserStagesResponse?) -> Unit) {
        getProgressApi(context).getUserStages( categoryId, page, limit).enqueue(object : Callback<UserStagesResponse> {
            override fun onResponse(call: Call<UserStagesResponse>, response: Response<UserStagesResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<UserStagesResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    // اضافه کردن متد جدید برای دریافت پیشرفت کاربر
    fun getUserProgress( onResult: (UserProgressResponse?) -> Unit) {
        getProgressApi(context).getUserProgress().enqueue(object : Callback<UserProgressResponse> {
            override fun onResponse(call: Call<UserProgressResponse>, response: Response<UserProgressResponse>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<UserProgressResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
