package com.vahidmohtasham.worddrag.screen.learned

import androidx.lifecycle.ViewModel
import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.api.responses.MarkWordLearnedRequest
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.screen.category.ProgressRepository
import com.vahidmohtasham.worddrag.screen.category.StageResponse
import com.vahidmohtasham.worddrag.screen.category.WordData
import com.vahidmohtasham.worddrag.utils.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LearnedWordsViewModel(apiService: ProgressApi) : ViewModel() {
    private val repository = ProgressRepository(apiService)
    private var userId: String? = null

    private val _selectedWords = MutableStateFlow<List<WordData>>(emptyList())
    val selectedWords: StateFlow<List<WordData>> = _selectedWords

    fun toggleWordSelection(word: WordData) {
        _selectedWords.value = if (_selectedWords.value.contains(word)) {
            _selectedWords.value - word // Remove the word if already selected
        } else {
            _selectedWords.value + word // Add the word if not selected
        }
    }

    fun submitLearnedWords(userId: String, stageId: String) {
        selectedWords.value.forEach { word ->
            val request = MarkWordLearnedRequest(userId, stageId, word.wordId._id)
            markWordLearned(request)
        }
    }

    private fun markWordLearned(request: MarkWordLearnedRequest) {
        repository.markWordLearned(request) {
            // Handle the response if necessary
        }
    }


      fun completeStage(request: CompleteStageRequest,onResult: (StageResponse?) -> Unit) {
        repository.completeStage(request,onResult)
    }
}

