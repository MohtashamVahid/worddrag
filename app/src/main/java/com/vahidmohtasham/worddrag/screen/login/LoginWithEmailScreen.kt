package com.vahidmohtasham.worddrag.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.viewmodels.UserViewModel
import com.vahidmohtasham.worddrag.screen.MainActivity
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle
import com.vahidmohtasham.worddrag.utils.NetworkUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithEmailScreen(navController: NavController, userViewModel: UserViewModel) {
    val context = LocalContext.current as MainActivity

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialogForgetPassword by remember { mutableStateOf(false) }
    val isLoading by userViewModel.isLoading.observeAsState(initial = false)
    val error by userViewModel.error.observeAsState()
    val loginResponse by userViewModel.loginResponse.observeAsState()
    val resetPasswordResponse by userViewModel.resetPasswordResponse.observeAsState()
    val passwordFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(resetPasswordResponse) {
        resetPasswordResponse?.let {
            Toast.makeText(context, "رمز عبور به ایمیل شما ارسال شد", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(loginResponse) {
        loginResponse?.let {
            navController.navigate("profile") {
                popUpTo("profile") { inclusive = true }
                launchSingleTop = true
            }
            Toast.makeText(context, "خوش آمدید", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ورود از طریق ایمیل",
                        Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        style = yekanBakhTextStyle,
                        textAlign = TextAlign.Center
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_playstore),
                    contentDescription = "لوگو",
                    modifier = Modifier
                        .size(250.dp) // پر کردن کامل فضای Box
                        .clip(RoundedCornerShape(24.dp)), // گوشه‌ها را گرد می‌کند
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "برای دسترسی به امکانات بیشتر وارد حساب کاربری خود شوید.",
                    style = yekanBakhTextStyle,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground, // رنگ جداساز
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp) // فاصله عمودی
                )
                TextField(
                    value = email,
                    maxLines = 1,
                    onValueChange = { email = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    label = { Text("ایمیل", style = yekanBakhTextStyle) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next // تنظیم اکشن صفحه کلید به Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { // هنگام فشار دادن دکمه Next
                            passwordFocusRequester.requestFocus() // انتقال فوکوس به TextField رمز عبور
                        }
                    )

                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = password,
                    maxLines = 1,
                    onValueChange = { password = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    label = { Text("رمز عبور", style = yekanBakhTextStyle) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                    visualTransformation = PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (email.isNotEmpty() && password.isNotEmpty())
                                userViewModel.loginWithEmail(email, password)

                            keyboardController?.hide()

                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp, // میزان برجستگی در حالت عادی
                        pressedElevation = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp), onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty())
                            userViewModel.loginWithEmail(email, password)
                    }, enabled = !isLoading
                ) {
                    Text(text = "ورود", style = yekanBakhTextStyle)
                }



                if (isLoading) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.height(8.dp))

                error?.let {
                    Text(text = it, color = Color.Red, style = yekanBakhTextStyle)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ثبت نام",
                    fontSize = 14.sp,
                    style = yekanBakhTextStyle,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickable {
                            navController.navigate("sign_up_screen")
                            userViewModel.clearError()
                        }
                )
//                Text(
//                    text = "ورود به عنوان میهمان",
//                    fontSize = 14.sp,
//                    style = yekanBakhTextStyle,
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp, horizontal = 16.dp)
//                        .clickable {
//                            val uniqueCode = userViewModel.getUniqueID()
//                            if (uniqueCode != null)
//                                userViewModel.loginGuest(uniqueCode)
//                        }
//                )

                Text(
                    text = "رمز عبور خود را فراموش کرده‌ام؟",
                    fontSize = 12.sp,
                    style = yekanBakhTextStyle,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clickable {
                            showDialogForgetPassword = true
                            userViewModel.clearError()
                        }
                )

            }

            if (showDialogForgetPassword) {
                ForgotPasswordDialog(onDismiss = { showDialogForgetPassword = false }) { email ->
                    showDialogForgetPassword = false
                    if (NetworkUtils.isNetworkAvailable(context)) {
                        userViewModel.resetPassword(email)
                    }
                }
            }
        }


    }
}

@Composable
fun ForgotPasswordDialog(
    onDismiss: () -> Unit,
    onEmailSubmitted: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "فراموشی رمز عبور", style = yekanBakhTextStyle) },
        text = {
            Column {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        cursorColor = MaterialTheme.colorScheme.onBackground
                    ),
                    label = { Text("ایمیل خود را وارد کنید", style = yekanBakhTextStyle) },
                    isError = !isEmailValid,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isEmailValid) {
                    Text(
                        text = "ایمیل وارد شده معتبر نیست!",
                        color = Color.Red,
                        style = yekanBakhTextStyle
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (isValidEmail(email)) {
                    onEmailSubmitted(email)  // Call the function to handle the email
                    onDismiss()
                } else {
                    isEmailValid = false  // Show error message if the email is invalid
                }
            }) {
                Text("ارسال", style = yekanBakhTextStyle)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("انصراف", style = yekanBakhTextStyle)
            }
        }
    )
}

// تابع برای بررسی صحت فرمت ایمیل
fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

