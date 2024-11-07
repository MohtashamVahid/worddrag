package com.vahidmohtasham.worddrag.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.adivery.sdk.Adivery
import com.vahidmohtasham.worddrag.AppItem
import com.vahidmohtasham.worddrag.BannerAdCardView
import com.vahidmohtasham.worddrag.BuildConfig
import com.vahidmohtasham.worddrag.filteredAppList
import com.vahidmohtasham.worddrag.screen.MainActivity
import com.vahidmohtasham.worddrag.screen.game.Difficulty
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle
import com.vahidmohtasham.worddrag.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDifficultyScreen(
    navController: NavHostController,
    categoryName: String,
    onDifficultySelected: (Difficulty) -> Unit,
    userViewModel: UserViewModel,
) {
    // ایجاد یک state برای ذخیره وضعیت انتخاب سختی
    var isDifficultySelected by remember { mutableStateOf(false) }
    val context = LocalContext.current as MainActivity

    LaunchedEffect(Unit) {
        if (!BuildConfig.DEBUG && !userViewModel.hasFreeTimeRemaining(BuildConfig.BANNER_AD_ID_Interstitial)) {
            if (Adivery.isLoaded(BuildConfig.BANNER_AD_ID_Interstitial)) {
                Adivery.showAd(BuildConfig.BANNER_AD_ID_Interstitial);
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Category: $categoryName") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Select Difficulty",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // جلوگیری از چند بار فراخوانی onDifficultySelected
            DifficultyButton(Difficulty.EASY) {
                if (!isDifficultySelected) {
                    isDifficultySelected = true
                    onDifficultySelected(Difficulty.EASY)
                }
            }
            DifficultyButton(Difficulty.MEDIUM) {
                if (!isDifficultySelected) {
                    isDifficultySelected = true
                    onDifficultySelected(Difficulty.MEDIUM)
                }
            }
            DifficultyButton(Difficulty.HARD) {
                if (!isDifficultySelected) {
                    isDifficultySelected = true
                    onDifficultySelected(Difficulty.HARD)
                }
            }

            BannerAdCardView(
                "7fa22e57-68c4-4157-85a0-485f7fa08f25", Modifier.padding(top = 16.dp, end = 8.dp, start = 8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(0.dp),
                horizontalAlignment = Alignment.Start
            ) {

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "اپلیکیشن‌های پیشنهادی",
                        style = yekanBakhTextStyle,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // اضافه کردن LazyVerticalGrid با ارتفاع مشخص
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp) // ارتفاع ثابت
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3), // تعداد ستون‌ها
                            modifier = Modifier.fillMaxSize() // پر کردن فضای موجود
                        ) {
                            items(filteredAppList) { app ->
                                AppItem(app, context) // آیتم اپلیکیشن
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp)) // فاصله پس از گرید
                }

            }
        }
    }
}
