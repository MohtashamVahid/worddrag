package com.vahidmohtasham.worddrag.screen.category

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.responses.UserProgressResponse

class ProgressViewModel(val context: Context) : ViewModel() {
    private val repository = ProgressRepository(context)

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()
    val startStageResponse = MutableLiveData<StageResponse?>()
    val markWordLearnedResponse = MutableLiveData<StageResponse?>()
    val completeStageResponse = MutableLiveData<StageResponse?>()
    val userStagesResponse = MutableLiveData<UserStagesResponse?>()
    val userProgressResponse = MutableLiveData<UserProgressResponse?>()

    fun startNewStage(request: StartNewStageRequest) {
        repository.startNewStage(request) {
            startStageResponse.value = it
        }
    }

    fun markWordLearned(request: MarkWordLearnedRequest) {
        repository.markWordLearned(request) {
            markWordLearnedResponse.value = it
        }
    }

    fun completeStage(request: CompleteStageRequest) {
        repository.completeStage(request) {
            completeStageResponse.value = it
        }
    }

    fun getUserStages( categoryId: String, page: Int = 1, limit: Int = 10) {
        repository.getUserStages(categoryId, page, limit) {
            userStagesResponse.value = it
        }
    }

    fun getUserProgress() {
        repository.getUserProgress() {
            userProgressResponse.value = it
        }
    }

    fun updateApiServices(context: Context) {
        repository.updateApiServices(context)
    }
}
