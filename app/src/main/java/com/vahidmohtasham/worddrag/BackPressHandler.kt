package com.vahidmohtasham.worddrag

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.navigation.NavHostController

@Composable
fun BackPressHandler(
    navController: NavHostController,
    showExitDialog: MutableState<Boolean>,
) {
    // مدیریت دکمه برگشت
    BackHandler {
        if (showExitDialog.value) {
            return@BackHandler
        } else {
            showExitDialog.value = true
        }
    }

    // نمایش دیالوگ تایید خروج
    if (showExitDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showExitDialog.value = false // وقتی دیالوگ بسته می‌شود
            },
            title = {
                Text("آیا می‌خواهید از این صفحه خارج شوید؟")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog.value = false
                        navController.popBackStack() // برگشت به صفحه قبلی
                    }
                ) {
                    Text("بله")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog.value = false // بستن دیالوگ اگر کاربر دکمه "نه" را بزند
                    }
                ) {
                    Text("نه")
                }
            }
        )
    }
}
