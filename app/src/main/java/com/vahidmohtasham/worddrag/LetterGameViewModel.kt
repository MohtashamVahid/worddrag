package com.vahidmohtasham.worddrag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LetterGameViewModel : ViewModel() {
    private val _state = MutableStateFlow(LetterGameState())
    val state: StateFlow<LetterGameState> = _state

    fun loadWords() {
        viewModelScope.launch {
            _state.value = LetterGameState(isLoading = true)
            try {
//                val words = RetrofitClient.apiService.getWords()
                _state.value = LetterGameState(words = listOf("test", "test", "test", "test"))
            } catch (e: IOException) {
                _state.value = LetterGameState(error = "Network Error")
            } catch (e: Exception) {
                _state.value = LetterGameState(error = "Server Error")
            }
        }
    }
}
