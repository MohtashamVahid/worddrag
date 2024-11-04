package com.vahidmohtasham.worddrag.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.vahidmohtasham.worddrag.BuildConfig
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle
import com.vahidmohtasham.worddrag.viewmodels.UserViewModel


@Composable
fun CheckAppUpdateScreen(viewModel: UserViewModel) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) } // متغیر حالت برای نمایش دیالوگ


    val config = viewModel.loadConfigFromPreferences(context)


    val currentVersionCode = AppUtils.getAppVersionCode(context)

    config?.androidVersion?.let { androidVersion ->
        if (currentVersionCode < androidVersion && showDialog) { // نمایش دیالوگ تنها وقتی showDialog true است
            if (config.forceUpdate) {
                ForceUpdateDialog(
                    context = context
                )
            } else {
                if (!viewModel.shouldShowUpdateDialog())
                    return
                RegularUpdateDialog(
                    onDismiss = {
                        showDialog = false
                        viewModel.savePrefLastUpdatePrompt()

                    },
                    onUpdate = {
                        openMarketLink(context) // لینک به‌روزرسانی بازار
                        showDialog = false // بستن دیالوگ پس از به‌روزرسانی
                    },
                    context = context
                )
            }
        }
    }
}


@Composable
fun ForceUpdateDialog(context: Context) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(text = "آپدیت اجباری", style = yekanBakhTextStyle, fontSize = 16.sp) },
            text = {
                Column {
                    Text("نسخه جدیدی از برنامه منتشر شده است و شما باید آن را به‌روزرسانی کنید.", style = yekanBakhTextStyle, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("لطفاً برای دانلود آخرین نسخه، به کافه بازار مراجعه کنید.", style = yekanBakhTextStyle, fontSize = 14.sp)
                }
            },
            confirmButton = {
                Button(onClick = {
                    openMarketLink(context)
                    if (context is Activity) {
                        context.finishAffinity()
                    }
                }) {
                    Text("دانلود از کافه بازار", style = yekanBakhTextStyle, fontSize = 14.sp)
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = {
                        if (context is Activity) {
                            context.finishAffinity()
                        }
                    }) {
                    Text("خروج", style = yekanBakhTextStyle, fontSize = 14.sp)
                }
            }
        )
    }
}


@Composable
fun RegularUpdateDialog(onDismiss: () -> Unit, onUpdate: () -> Unit, context: Context) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            color = MaterialTheme.colorScheme.surface, // تنظیم رنگ پس‌زمینه از تم
            shape = MaterialTheme.shapes.medium // تنظیم شکل سطح
        ) {
            AlertDialog(
                onDismissRequest = onDismiss, // این تابع وقتی کاربر خارج از دیالوگ کلیک کند، دیالوگ را می‌بندد
                title = {
                    Text(
                        text = "به‌روزرسانی موجود است",
                        style = yekanBakhTextStyle,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                text = {
                    Column {
                        Text("نسخه جدیدی از برنامه در دسترس است. می‌خواهید آن را به‌روزرسانی کنید؟", style = yekanBakhTextStyle, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("لطفاً برای دانلود آخرین نسخه، به کافه بازار مراجعه کنید.", style = yekanBakhTextStyle, fontSize = 14.sp)
                    }
                },
                confirmButton = {
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            openMarketLink(context) // این تابع لینک بازار را باز می‌کند
                            onDismiss() // این تابع دیالوگ را می‌بندد
                        }
                    ) {
                        Text("به‌روزرسانی", style = yekanBakhTextStyle, fontSize = 14.sp)
                    }
                },
                dismissButton = {
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            onDismiss() // این تابع وقتی روی "بعداً" کلیک شود، دیالوگ را می‌بندد
                        }
                    ) {
                        Text("بعداً", style = yekanBakhTextStyle, fontSize = 14.sp)
                    }
                }
            )
        }
    }
}


private fun openMarketLink(context: Context) {
    val packageName = BuildConfig.APPLICATION_ID
    val uri = Uri.parse("market://details?id=$packageName")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        val webUri = Uri.parse("https://cafebazaar.ir/app/$packageName")
        val webIntent = Intent(Intent.ACTION_VIEW, webUri)
        context.startActivity(webIntent)
    }
}
