package com.vahidmohtasham.worddrag.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "حریم خصوصی", Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, fontSize = 14.sp, style = yekanBakhTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                item {
                    Text(text = "سیاست حفظ حریم خصوصی", style = yekanBakhTextStyle, fontSize = 14.sp)

                    Text(text = "ما به حریم خصوصی کاربران احترام می‌گذاریم و متعهد هستیم که اطلاعات شخصی شما را به‌طور کامل محافظت کنیم. این سیاست‌نامه به شما کمک می‌کند تا از نحوه جمع‌آوری، استفاده و ذخیره اطلاعات شخصی خود مطلع شوید. لطفاً قبل از استفاده از خدمات ما، این سند را به دقت مطالعه نمایید. استفاده از خدمات به منزله پذیرش این سیاست است.")
                }
                item {
                    Text(text = "۱. تغییرات در سیاست حفظ حریم خصوصی", style = yekanBakhTextStyle, fontSize = 14.sp)
                    Text(text = "این سند ممکن است به‌روزرسانی شود و هرگونه تغییر در این سیاست‌نامه از طریق اطلاع‌رسانی‌های مربوط به حساب کاربری شما اعلام می‌گردد. ما توصیه می‌کنیم که به‌طور مرتب این سند را مطالعه کنید.")

                }
                item {
                    Text(text = "۲. اطلاعاتی که جمع‌آوری می‌کنیم", style = yekanBakhTextStyle, fontSize = 14.sp)
                    Text(text = "اطلاعات شخصی و غیرشخصی ممکن است از طریق استفاده از سرویس‌های ما جمع‌آوری شوند، از جمله:\n- اطلاعات دستگاه و سیستم‌عامل\n- شناسه کاربری و شماره تلفن همراه\n- گزارش‌های فنی و عملکرد سرویس‌ها\n- جستجوها و انتخاب‌های شما در برنامه‌ها")

                }
                item {
                    Text(text = "۳. نحوه استفاده از اطلاعات", style = yekanBakhTextStyle, fontSize = 14.sp)
                    Text(text = "ما از اطلاعات شما برای بهبود عملکرد سرویس‌ها، ارائه پیشنهادهای اختصاصی، تحلیل رفتار کاربران، و ارتقاء امنیت استفاده می‌کنیم. همچنین ممکن است اطلاعات شما در صورت درخواست مراجع قانونی در اختیار آن‌ها قرار گیرد.")

                }
                item {
                    Text(text = "۴. اشتراک‌گذاری اطلاعات با اشخاص ثالث", style = yekanBakhTextStyle, fontSize = 14.sp)

                    Text(text = "اطلاعات شما تنها در چارچوب این سیاست‌نامه و در صورت نیاز با اشخاص ثالثی که با ما همکاری دارند، به اشتراک گذاشته می‌شود. ما مسئولیت استفاده از اطلاعات توسط اشخاص ثالث را بر عهده نداریم.")
                }
                item {
                    Text(text = "۵. امنیت اطلاعات شما", style = yekanBakhTextStyle, fontSize = 14.sp)

                    Text(text = "ما تدابیر لازم را برای حفاظت از اطلاعات شما اتخاذ کرده‌ایم، اما تضمینی برای امنیت کامل وجود ندارد. شما نیز مسئولیت حفظ امنیت اطلاعات خود از جمله رمز عبور را برعهده دارید.")
                }
                item {

                    Text(text = "۶. حقوق شما در مورد اطلاعات", style = yekanBakhTextStyle, fontSize = 14.sp)
                    Text(text = "شما می‌توانید درخواست دسترسی، اصلاح یا حذف اطلاعات شخصی خود را داشته باشید. ما در چارچوب قانون به درخواست‌های شما پاسخ خواهیم داد.")
                }
                item {

                    Text(
                        text = "برای هر گونه سوال یا درخواست، لطفاً با ما از طریق ایمیل پشتیبانی تماس بگیرید.",
                        style = yekanBakhTextStyle,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}