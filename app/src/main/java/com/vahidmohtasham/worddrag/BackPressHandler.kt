package com.vahidmohtasham.worddrag

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.navigation.NavHostController

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun BackPressHandler(
    onBackPressed: () -> Unit, // تابعی که شما میخواهید اجرا شود زمانی که دکمه برگشت فشرده می‌شود
) {
    BackHandler {
        onBackPressed() // اجرای تابع دلخواه زمانی که دکمه برگشت زده می‌شود
    }
}
