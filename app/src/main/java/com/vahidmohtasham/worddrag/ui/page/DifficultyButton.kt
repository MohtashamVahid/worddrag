package com.vahidmohtasham.worddrag.ui.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vahidmohtasham.worddrag.Difficulty

@Composable
fun DifficultyButton(
    difficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit
) {
    Button(
        onClick = { onDifficultySelected(difficulty) },
        colors = ButtonDefaults.buttonColors(
            containerColor = when (difficulty) {
                Difficulty.EASY -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) // سبز ملایم‌تر برای سطح آسان
                Difficulty.MEDIUM -> MaterialTheme.colorScheme.secondary // آبی ملایم برای سطح متوسط
                Difficulty.HARD -> MaterialTheme.colorScheme.tertiary // زرد ملایم برای سطح سخت
            },
            contentColor = MaterialTheme.colorScheme.onPrimary // رنگ متن برای هماهنگی بیشتر
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Text(
            text = difficulty.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
