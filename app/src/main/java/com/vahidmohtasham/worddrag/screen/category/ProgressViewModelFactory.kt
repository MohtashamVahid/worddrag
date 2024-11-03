package com.vahidmohtasham.worddrag.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahidmohtasham.worddrag.api.ProgressApi

class ProgressViewModelFactory(private val apiService: ProgressApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
