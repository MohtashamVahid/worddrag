package com.vahidmohtasham.worddrag.screen.learned

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahidmohtasham.worddrag.api.ProgressApi
import com.vahidmohtasham.worddrag.screen.category.ProgressRepository

class LearnedWordsViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearnedWordsViewModel::class.java)) {
            return LearnedWordsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
