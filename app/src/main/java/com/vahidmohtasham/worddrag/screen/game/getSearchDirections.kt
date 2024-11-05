package com.vahidmohtasham.worddrag.screen.game

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.BannerAdCardView
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModel
import com.vahidmohtasham.worddrag.screen.category.WordData
import com.vahidmohtasham.worddrag.screen.category.WordDetails
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle


fun getSearchDirections(difficulty: Difficulty): String {
    return when (difficulty) {
        Difficulty.EASY -> {
            "شما می‌توانید به دنبال کلمات در این سمت‌ها باشید:\n" +
                    "- افقی: چپ به راست\n" +
                    "- عمودی: بالا به پایین"
        }

        Difficulty.MEDIUM -> {
            "شما می‌توانید به دنبال کلمات در این سمت‌ها باشید:\n" +
                    "- افقی: چپ به راست\n" +
                    "- عمودی: بالا به پایین\n" +
                    "- افقی: راست به چپ\n" +
                    "- عمودی: پایین به بالا"
        }

        Difficulty.HARD -> {
            "شما می‌توانید به دنبال کلمات در این سمت‌ها باشید:\n" +
                    "- افقی: چپ به راست\n" +
                    "- عمودی: بالا به پایین\n" +
                    "- افقی: راست به چپ\n" +
                    "- عمودی: پایین به بالا\n" +
                    "- مورب: چپ-بالا به راست-پایین\n" +
                    "- مورب: راست-پایین به چپ-بالا\n" +
                    "- مورب: راست-بالا به چپ-پایین\n" +
                    "- مورب: چپ-پایین به راست-بالا"
        }
    }
}

@Composable
fun DirectionsScreen(difficulty: Difficulty, showDialog: Boolean, onDismiss: () -> Unit) {
    val directions = getSearchDirections(difficulty)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("راهنمای پیدا کردن کلمات", style = yekanBakhTextStyle, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // نمایش دیالوگ در صورت نیاز
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = { Text("سمت‌های جستجو", style = yekanBakhTextStyle, fontSize = 14.sp) },
                    text = { Text(directions, style = yekanBakhTextStyle, fontSize = 14.sp) },
                    confirmButton = {
                        TextButton(onClick = onDismiss) {
                            Text("بستن", style = yekanBakhTextStyle, fontSize = 14.sp)
                        }
                    }
                )
            }
        }
    }
}