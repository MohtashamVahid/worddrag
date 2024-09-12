package com.vahidmohtasham.worddrag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import com.vahidmohtasham.worddrag.ui.theme.PrimaryColor
import com.vahidmohtasham.worddrag.ui.theme.Purple80
import com.vahidmohtasham.worddrag.ui.theme.onPrimaryColor

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
            TopAppBar(title = { Text("User Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =  PrimaryColor, // رنگ پس‌زمینه تولبار
                    titleContentColor = onPrimaryColor // رنگ متن عنوان تولبار
                )
                )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello, $userName!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = score / 100f,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Level: $level", fontSize = 18.sp)
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
            .padding(vertical = 4.dp)
    ) {
        Text(text = difficulty.toString())
    }
}
