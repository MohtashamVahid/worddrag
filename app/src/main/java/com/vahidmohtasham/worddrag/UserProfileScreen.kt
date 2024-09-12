package com.vahidmohtasham.worddrag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import com.vahidmohtasham.worddrag.ui.theme.BackgroundColor
import com.vahidmohtasham.worddrag.ui.theme.ButtonColor
import com.vahidmohtasham.worddrag.ui.theme.PrimaryColor
import com.vahidmohtasham.worddrag.ui.theme.TextPrimaryColor
import com.vahidmohtasham.worddrag.ui.theme.TextSecondaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    userName: String,
    score: Int,
    level: String,
    onDifficultySelected: (Difficulty) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryColor, // رنگ پس‌زمینه تولبار
                    titleContentColor = TextPrimaryColor // رنگ متن عنوان تولبار
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "Hello, $userName!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimaryColor)
            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = score / 100f,
                color = ButtonColor, // رنگ نوار پیشرفت
                trackColor = TextSecondaryColor, // رنگ پس‌زمینه‌ی نوار
                modifier = Modifier
                    .fillMaxWidth() // پر کردن تمام عرض صفحه
                    .padding(vertical = 8.dp, horizontal = 40.dp) // پدینگ
                    .height(8.dp) // افزایش ضخامت نوار پیشرفت
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Level: $level", fontSize = 18.sp, color = TextSecondaryColor)
            Spacer(modifier = Modifier.height(16.dp))
            DifficultyButton(Difficulty.EASY, onDifficultySelected)
            DifficultyButton(Difficulty.MEDIUM, onDifficultySelected)
            DifficultyButton(Difficulty.HARD, onDifficultySelected)
        }
    }
}

@Composable
fun DifficultyButton(difficulty: Difficulty, onClick: (Difficulty) -> Unit) {
    Button(
        onClick = { onClick(difficulty) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 40.dp), colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor, // رنگ پس‌زمینه
            contentColor = TextPrimaryColor, // رنگ متن
            disabledContainerColor = ButtonColor, // رنگ پس‌زمینه در حالت غیرفعال
            disabledContentColor = Color.Gray // رنگ متن در حالت غیرفعال
        )
    ) {
        Text(text = difficulty.toString())
    }
}
