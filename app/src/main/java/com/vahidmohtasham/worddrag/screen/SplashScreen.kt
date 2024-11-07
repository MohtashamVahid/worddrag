package com.vahidmohtasham.worddrag.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vahidmohtasham.worddrag.BuildConfig
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val images = listOf(
        R.drawable.splash_1,
        R.drawable.splash_2,
        R.drawable.splash_3
    )

    val randomImage = remember { images.random() }

    Box(
        modifier = Modifier
            .fillMaxSize()

            .padding(bottom = 40.dp)
            .background(Color.Black), // پس‌زمینه مشکی برای حالت فول اسکرین
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = randomImage),
            contentDescription = null,
            contentScale = ContentScale.Crop, // تصویر به صورت تمام‌صفحه نمایش داده می‌شود
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "This application uses OpenAI models for content processing and generation.",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Vahid Mohtasham",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Version: ${BuildConfig.VERSION_NAME}",
                fontSize = 12.sp,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp )
                .height(4.dp)
                .background(Color.Gray) // رنگ پس‌زمینه نوار بارگذاری
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                trackColor = Color.Black
            )
        }
    }

    // اجرای تایمر 2 ثانیه‌ای برای اسپلش‌اسکرین
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
}
