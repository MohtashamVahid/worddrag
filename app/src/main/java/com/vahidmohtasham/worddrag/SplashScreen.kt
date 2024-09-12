package com.vahidmohtasham.worddrag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vahidmohtasham.worddrag.ui.theme.BackgroundColor
import com.vahidmohtasham.worddrag.ui.theme.TextPrimaryColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), // عرض را پر می‌کند
            horizontalAlignment = Alignment.CenterHorizontally // متن‌ها را به صورت افقی وسط‌چین می‌کند
        ) {
            Text(
                text = "Welcome To",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryColor
            )
            Text(
                text = "English Word Puzzle",
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                color = TextPrimaryColor
            )
        }

    }

    LaunchedEffect(Unit) {
        delay(2000) // Wait for 2 seconds
        onTimeout()
    }
}
