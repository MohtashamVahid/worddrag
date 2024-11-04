package com.vahidmohtasham.worddrag.screen.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.responses.UserProgressResponse

class ProgressViewModel(apiService: ProgressApi) : ViewModel() {
    private val repository = ProgressRepository(apiService)

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

    fun getUserStages(userId: String, categoryId: String, page: Int = 1, limit: Int = 10) {
        repository.getUserStages(userId, categoryId, page, limit) {
            userStagesResponse.value = it
        }
    }

    fun getUserProgress(userId: String) {
        repository.getUserProgress(userId) {
            userProgressResponse.value = it
        }
    }
}
