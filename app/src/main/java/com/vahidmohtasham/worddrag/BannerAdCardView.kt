package com.vahidmohtasham.worddrag

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.adivery.sdk.AdiveryAdListener
import com.adivery.sdk.AdiveryBannerAdView
import com.adivery.sdk.BannerSize

@Composable
fun BannerAdCardView(placementId: String, modifier: Modifier = Modifier) {
    var showAds by remember { mutableStateOf(true) }

    if (showAds  ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),  // پر کردن عرض کارت
            shape = RoundedCornerShape(16.dp),  // شعاع گوشه‌های کارت
            elevation = CardDefaults.cardElevation(8.dp)  // سایه برای کارت
        ) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(), // پر کردن عرض فضای موجود
                factory = { context ->
                    AdiveryBannerAdView(context).apply {
                        setPlacementId(placementId)  // مقدار placementId را اینجا تنظیم کنید

                        // استفاده از بنر هوشمند که طول صفحه را می‌گیرد و ارتفاع را به صورت wrap content تنظیم می‌کند
                        setBannerSize(BannerSize.SMART_BANNER)

                        setBannerAdListener(object : AdiveryAdListener() {
                            override fun onAdLoaded() {
                                // تبلیغ بارگذاری شده است
                            }

                            override fun onError(reason: String) {
                                showAds = false
                            }

                            override fun onAdClicked() {
                                // کاربر روی تبلیغ کلیک کرده است
                            }
                        })

                        loadAd()
                    }
                }
            )
        }
    }
}

