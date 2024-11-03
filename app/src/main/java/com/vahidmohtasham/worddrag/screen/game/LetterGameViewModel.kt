package com.vahidmohtasham.worddrag.screen.game

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahidmohtasham.worddrag.screen.category.WordData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LetterGameViewModel : ViewModel() {
    private val _state = MutableStateFlow(LetterGameState())
    val state: StateFlow<LetterGameState> = _state

      val _foundWords = mutableStateListOf<String>()
    val foundWords: List<String> get() = _foundWords

    // MutableState برای فعال یا غیرفعال کردن دکمه
    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    val allWordsFound: Boolean
        get() = foundWords.size == _state.value.words.size

    fun loadWords(words: List<WordData>) {
        viewModelScope.launch {
            _state.value = LetterGameState(isLoading = true)
            try {
                val wordsListString = words.map { wordData ->
                    "${wordData.wordId.word}"
                }
                _state.value = LetterGameState(words = wordsListString)
                // بررسی وضعیت دکمه بعد از بارگذاری کلمات
                _isButtonEnabled.value = allWordsFound
            } catch (e: IOException) {
                _state.value = LetterGameState(error = "Network Error")
            } catch (e: Exception) {
                _state.value = LetterGameState(error = "Server Error")
            }
        }
    }

    fun addFoundWord(word: String) {
        if (!_foundWords.contains(word)) {
            _foundWords.add(word)
            Log.d("LetterGameViewModel", "Found word added: $word") // لاگ برای بررسی
            _isButtonEnabled.value = allWordsFound
        }
    }

}
