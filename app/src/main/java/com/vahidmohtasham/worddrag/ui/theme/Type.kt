package com.vahidmohtasham.worddrag.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vahidmohtasham.worddrag.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.kavoon_regular, FontWeight.Normal)
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.kavoon_regular, FontWeight.Normal)
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle( // استایل مخصوص دکمه‌ها
        fontFamily = FontFamily(
            Font(R.font.kavoon_regular, FontWeight.Normal) // فونت سفارشی شما
        ),
        fontWeight = FontWeight.Bold, // وزن دلخواه
        fontSize = 14.sp, // اندازه فونت دلخواه
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.kavoon_regular, FontWeight.Normal)
        ),
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)


val yekanbakhRegularFontFamily = FontFamily(
    Font(R.font.yekanbakh_regular) // Replace with your font file
)

val yekanBakhTextStyle = TextStyle(
    fontFamily = yekanbakhRegularFontFamily,
    fontSize = 16.sp // Adjust the size as needed
)






