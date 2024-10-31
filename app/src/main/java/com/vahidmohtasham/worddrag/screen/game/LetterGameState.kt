package com.vahidmohtasham.worddrag.screen.game

data class LetterGameState(
    val words: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val score: Int = 0,
    val error: String? = null
)

// Difficulty levels
enum class Difficulty {
    EASY, MEDIUM, HARD
}