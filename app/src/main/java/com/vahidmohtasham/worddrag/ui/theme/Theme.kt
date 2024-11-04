package com.vahidmohtasham.worddrag.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.vahidmohtasham.worddrag.R

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    secondary = Color(0xFFf3ebe4),
    tertiary = DarkTertiaryColor,
    onPrimary = DarkTextPrimaryColor,
    onSecondary = Color(0xFF1E1E1E),
    surface = Color(0xFF1E1E1E), // رنگ تیره‌تر برای پس‌زمینه در حالت تاریک
    onSurface = DarkTextPrimaryColor, // رنگ متن روی پس‌زمینه در حالت تاریک
    background = DarkBackgroundColor,//Color(0xFF121212) // رنگ پس‌زمینه کلی
    onBackground = Color(0xFFf3ebe4) // رنگ متن روی پس‌زمینه
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFB2DFDB), // سبز مایل به آبی ملایم
    secondary = Color(0xFF80DEEA), // آبی ملایم
    tertiary = Color(0xFFFFD54F), // زرد ملایم برای اعلان‌ها
    onPrimary = Color(0xFF004D40), // سبز تیره برای متن روی رنگ‌های اصلی
    onSecondary = Color(0xFFFFFFFF), // سفید برای متن روی دکمه‌های آبی
    surface = Color(0xFFE0F7FA), // آبی خیلی ملایم برای پس‌زمینه
    onSurface = Color(0xFF00695C), // سبز تیره برای متن روی سطح روشن
    background = Color(0xFFF1F8E9), // سبز روشن مایل به زرد برای پس‌زمینه اصلی اپلیکیشن
    onBackground = Color(0xFF212121) // خاکستری تیره برای متن‌های اصلی
)


@Composable
fun WordDragTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}