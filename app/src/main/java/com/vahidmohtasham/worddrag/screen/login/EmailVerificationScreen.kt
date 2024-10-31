package com.vahidmohtasham.worddrag.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
  import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.api.UserViewModel
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(
    navController: NavHostController,
    viewModel: UserViewModel

) {
    val context = LocalContext.current
    val timeRemaining = viewModel.timeRemaining.observeAsState(initial = 0)
    val isLoading = viewModel.isLoading.observeAsState(initial = false)
    val error = viewModel.error.observeAsState(initial = null)
    val verifyEmailResponse = viewModel.verifyEmailResponse.observeAsState()
    val email = remember { mutableStateOf("") }
    val verificationCode = remember { mutableStateOf("") }

    LaunchedEffect(verifyEmailResponse.value) {
        verifyEmailResponse.value?.let {
            navController.navigate("sessions") {
                popUpTo("sessions") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "تایید ایمیل", Modifier.fillMaxWidth(),
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
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "لطفا کد تایید ارسال شده به ایمیل خود را اینجا وارد کنید.",
                    style = yekanBakhTextStyle,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_playstore),
                    contentDescription = "لوگو",
                    modifier = Modifier
                        .size(250.dp) // پر کردن کامل فضای Box
                        .clip(RoundedCornerShape(24.dp)), // گوشه‌ها را گرد می‌کند
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))



                TextField(
                    value = verificationCode.value,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.onBackground,

                        ),
                    onValueChange = { verificationCode.value = it },
                    label = { Text("کد تایید", style = yekanBakhTextStyle) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (error.value != null) {
                    Text(
                        text = error.value ?: "",
                        color = Color.Red,
                        style = yekanBakhTextStyle
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.verifyEmail(email.value, verificationCode.value)
                    },
                    enabled = !isLoading.value,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("تایید", style = yekanBakhTextStyle)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (timeRemaining.value > 0) {
                    Text(
                        text = "لطفاً ${timeRemaining.value} ثانیه صبر کنید",
                        style = yekanBakhTextStyle,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Button(
                        onClick = {
                            viewModel.resendVerificationEmail(email.value)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ارسال مجدد کد تایید", style = yekanBakhTextStyle)
                    }
                }
            }
        }
    }
}
