package com.vahidmohtasham.worddrag.screen.learned

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.screen.category.ProgressRepository

class LearnedWordsViewModelFactory(private val apiService: ProgressApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearnedWordsViewModel::class.java)) {
            return LearnedWordsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
