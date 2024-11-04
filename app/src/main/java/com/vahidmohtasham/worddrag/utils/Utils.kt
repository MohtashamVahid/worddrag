package com.vahidmohtasham.worddrag.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalDensity


@Composable
fun Float.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}

object AppUtils {

    @JvmStatic
    fun getAppVersionCode(context: Context): Long {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }

    @JvmStatic
    fun getAppVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }
}


