package com.vahidmohtasham.worddrag.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.vahidmohtasham.worddrag.viewmodels.UserViewModel
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.BackPressHandler
import com.vahidmohtasham.worddrag.BannerAdCardView
import com.vahidmohtasham.worddrag.BuildConfig
import com.vahidmohtasham.worddrag.api.entity.Category
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle


@Composable
fun UserProfileScreen(
    navHostController: NavHostController,
    userName: String,
    score: Int,
    level: String,
    onCategorySelected: (Category) -> Unit,
    userViewModel: UserViewModel
) {
    val categories by userViewModel.categories.observeAsState() // دریافت دسته‌ها از ViewModel
    val context = LocalContext.current

    val showExitDialog = remember { mutableStateOf(false) } // برای نمایش دیالوگ تایید خروج

    // استفاده از BackPressHandler برای مدیریت دکمه برگشت
    BackPressHandler(navController = navHostController, showExitDialog = showExitDialog)


    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // اعمال حاشیه‌ها
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // فاصله بین عناصر
        ) {
            item {
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
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Select Category:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // استفاده از LazyColumn برای دسته‌بندی‌ها
            categories?.let {
                items(it.chunked(3)) { rowCategories -> // تقسیم دسته‌ها به ردیف‌هایی با 3 دسته
                    LazyRow(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth() // پر کردن عرض
                    ) {
                        items(rowCategories) { category ->
                            CategoryButton(category, onClick = onCategorySelected)
                        }
                    }
                }
            }

            item {

                BannerAdCardView(
                    BuildConfig.BANNER_AD_ID_PROFILE, Modifier.padding(top = 8.dp, end = 8.dp, start = 8.dp, bottom = 8.dp)
                )
             }

            // نمایش هشدار تأیید ایمیل
            item {
                if (!userViewModel.isTokenExpired(context) && !userViewModel.isEmailVerified()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, start = 16.dp, end = 16.dp)
                            .clickable(onClick = {
                                navHostController.navigate("email_verification_screen")
                            }),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(horizontal = 8.dp),
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = "ایمیل شما تأیید نشده است! برای دسترسی به تمام ویژگی‌ها، لطفاً ایمیل خود را تأیید کنید",
                                style = yekanBakhTextStyle,
                                fontSize = 14.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {navHostController.navigate("resources_page") },
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // رنگ جدید دکمه‌ها
                        contentColor = MaterialTheme.colorScheme.onPrimary // رنگ متن
                    ),
                    shape = MaterialTheme.shapes.small, // استفاده از شکل کوچک‌تر برای دکمه‌ها
                    elevation = ButtonDefaults.buttonElevation(4.dp) // سایه ملایم
                ) {
                    Text(
                        text = "View Resources", fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun CategoryButton(category: Category, onClick: (Category) -> Unit) {
    Button(
        onClick = { onClick(category) },
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp) // حذف padding جانبی برای مناسب‌تر شدن دکمه‌ها
            .height(50.dp), // ارتفاع دکمه‌ها را کاهش می‌دهیم
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // رنگ جدید دکمه‌ها
            contentColor = MaterialTheme.colorScheme.onPrimary // رنگ متن
        ),
        shape = MaterialTheme.shapes.small, // استفاده از شکل کوچک‌تر برای دکمه‌ها
        elevation = ButtonDefaults.buttonElevation(4.dp) // سایه ملایم
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium, // تغییر به bodyMedium برای وضوح بیشتر
        )
    }
}
