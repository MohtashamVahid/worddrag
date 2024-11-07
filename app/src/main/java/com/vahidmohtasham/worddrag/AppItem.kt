package com.vahidmohtasham.worddrag


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle

@Composable
fun AppItem(app: App, context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                BazaarIntentManager(context).openAppDetails(app.packageName)
            }
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = app.imageUrl,
            contentDescription = app.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape), // گرد کردن تصویر
            contentScale = ContentScale.Crop // اطمینان از برش درست تصویر
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = app.name,
            style = yekanBakhTextStyle,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

data class App(
    val name: String,
    val imageUrl: String,
    val marketLink: String,
    val packageName: String
)

const val currentPackage = BuildConfig.APPLICATION_ID

val appList = listOf(
    App(
        "لوگوساز حرفه ای و با کیفیت",
        "https://s.cafebazaar.ir/images/icons/com.vahidmohtasham.samantaai.logomaker-06e6e135-dcd3-47c8-b003-6948b1b07451_512x512.png?x-img=v1/format,type_webp,lossless_false/resize,h_256,w_256,lossless_false/optimize",
        "https://cafebazaar.ir/app/com.vahidmohtasham.samantaai.logomaker",
        "com.vahidmohtasham.samantaai.logomaker"
    ),
    App(
        "گرفتن فال با سامانتا",
        "https://s.cafebazaar.ir/images/icons/com.vahidmohtasham.samantaai.falegir-9cb181ec-6f69-4423-96a4-5f6ace89daa8_512x512.png?x-img=v1/format,type_webp,lossless_false/resize,h_256,w_256,lossless_false/optimize",
        "https://cafebazaar.ir/app/com.vahidmohtasham.samantaai.falegir",
        "com.vahidmohtasham.samantaai.falegir"
    ),
    App(
        "هوش مصنوعی سامانتا",
        "https://s.cafebazaar.ir/images/icons/com.vahidmohtasham.samantaai-24a50fb8-1a77-4f9a-84d3-8b32694e13f9_512x512.png?x-img=v1/format,type_webp,lossless_false/resize,h_256,w_256,lossless_false/optimize",
        "https://cafebazaar.ir/app/com.vahidmohtasham.samantaai",
        "com.vahidmohtasham.samantaai"
    ),
    App(
        "تحلیل سایت با هوش مصنوعی",
        "https://s.cafebazaar.ir/images/icons/com.vahidmohtasham.samantaai.ai_analysis-45d649b8-2408-40aa-8505-4f1beb52356f_512x512.png?x-img=v1/format,type_webp,lossless_false/resize,h_256,w_256,lossless_false/optimize",
        "https://cafebazaar.ir/app/com.vahidmohtasham.samantaai.ai_analysis",
        "com.vahidmohtasham.samantaai.ai_analysis"
    ),
    App(
        "تبدیل متن به تصویر آسان",
        "https://s.cafebazaar.ir/images/upload/icons/com.vahidmohtasham.samantaai.text_to_image-b951543e-b0a3-41a4-a654-b0cf1a0a416a.png",
        "https://cafebazaar.ir/app/com.vahidmohtasham.samantaai.text_to_image",
        "com.vahidmohtasham.samantaai.text_to_image"
    ),
    App(
        "شبیه ساز تاچ و کلیکر",
        "https://s.cafebazaar.ir/images/icons/com.touch.gram-1d295dca-36b4-49f8-b79c-6f7fc56835a7_512x512.png?x-img=v1/format,type_webp,lossless_false/resize,h_256,w_256,lossless_false/optimize",
        "https://cafebazaar.ir/app/com.touch.gram",
        "com.touch.gram"
    ),
)

val filteredAppList = appList.filterNot { app ->
    app.packageName == currentPackage
}
