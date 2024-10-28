package com.vahidmohtasham.worddrag

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // لیست تصاویر ریسورس
    val images = listOf(
        R.drawable.splash_1,
        R.drawable.splash_2,
        R.drawable.splash_3
    )

    // انتخاب تصادفی یک تصویر از لیست
    val randomImage = remember { images.random() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        // نمایش تصویر تمام صفحه
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
                text = "Version: ${BuildConfig.VERSION_NAME}",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                text = "Vahid Mohtasham",
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 0.dp)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.primary)
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
