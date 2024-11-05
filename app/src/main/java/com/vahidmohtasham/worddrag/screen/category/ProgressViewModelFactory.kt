package com.vahidmohtasham.worddrag.screen.category

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahidmohtasham.worddrag.api.ProgressApi

class ProgressViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
