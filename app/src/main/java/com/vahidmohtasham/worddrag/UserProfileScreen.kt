package com.vahidmohtasham.worddrag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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


@Composable
fun UserProfileScreen(
    userName: String,
    score: Int,
    level: String,
    onDifficultySelected: (Difficulty) -> Unit,
    onCategorySelected: (String) -> Unit // افزودن تابع برای انتخاب دسته‌بندی
) {
    val categories = listOf(
        "Travel",
        "Food",
        "Education",
        "Science",
        "Sports",
        "Entertainment",
        "Art",
        "History",
        "Nature",
        "Technology",
        "Health",
        "Finance",
        "Lifestyle",
        "Fashion",
        "Music",
        "Movies",
        "Gaming"
    )

    Scaffold(

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(text = "Hello, $userName!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = score / 100f,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 40.dp)
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Level: $level", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // اضافه کردن دسته‌بندی‌ها
            Text(text = "Select Category:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // استفاده از LazyColumn برای دسته‌بندی‌ها
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // فاصله بین دسته‌ها
            ) {
                items(categories.chunked(3)) { rowCategories -> // تقسیم دسته‌ها به ردیف‌هایی با 3 دسته
                    LazyRow(
                        horizontalArrangement = Arrangement.Center, // وسط‌چین کردن دکمه‌ها
                        modifier = Modifier.fillMaxWidth() // پر کردن عرض
                    ) {
                        items(rowCategories) { category ->
                            CategoryButton(category = category, onClick = onCategorySelected)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryButton(category: String, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(category) },
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp) // حذف padding جانبی برای مناسب‌تر شدن دکمه‌ها
            .height(50.dp), // ارتفاع دکمه‌ها را کاهش می‌دهیم
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // رنگ جدید دکمه‌ها
            contentColor =  MaterialTheme.colorScheme.onPrimary // رنگ متن
        ),
        shape = MaterialTheme.shapes.small, // استفاده از شکل کوچک‌تر برای دکمه‌ها
        elevation = ButtonDefaults.buttonElevation(4.dp) // سایه ملایم
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium, // تغییر به bodyMedium برای وضوح بیشتر
        )
    }
}
