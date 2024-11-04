package com.vahidmohtasham.worddrag.screen.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.R

import com.vahidmohtasham.worddrag.viewmodels.UserViewModel
import com.vahidmohtasham.worddrag.screen.MainActivity
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val context = LocalContext.current as MainActivity

    // متغیرهای متنی
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // تعریف FocusRequester برای هر فیلد
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
     val passwordFocusRequester = remember { FocusRequester() }

    // بارگذاری وضعیت
    val registerResponse by userViewModel.registrationResponse.observeAsState()
    val isLoading by userViewModel.isLoading.observeAsState(false)
    val error by userViewModel.error.observeAsState()

    LaunchedEffect(registerResponse) {
        registerResponse?.let {
            Toast.makeText(context, it.getMessageOrError() ?: "", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            userViewModel.clearRegisterResponse()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Register",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // فاصله بین آیتم‌ها
            ) {
                item {

                    isLoading?.let {
                        if (it)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background,
                                    trackColor = MaterialTheme.colorScheme.secondary
                                )
                            }
                    }
                }

                item {

                    TextField(
                        value = firstName,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = { firstName = it },
                        label = { Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .focusRequester(firstNameFocusRequester), // FocusRequester برای نام
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // اکشن صفحه کلید
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { lastNameFocusRequester.requestFocus() } // جابه‌جایی فوکوس به نام خانوادگی
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // فیلد نام

                item {

                    TextField(
                        value = lastName,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = { lastName = it },
                        label = { Text("Family") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .focusRequester(lastNameFocusRequester), // FocusRequester برای نام خانوادگی
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // اکشن صفحه کلید
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { emailFocusRequester.requestFocus() } // جابه‌جایی فوکوس به ایمیل
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // فیلد نام خانوادگی

                item {

                    TextField(
                        value = email,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .focusRequester(emailFocusRequester), // FocusRequester برای ایمیل
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // اکشن صفحه کلید
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { passwordFocusRequester.requestFocus() } // جابه‌جایی فوکوس به شماره تلفن
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // فیلد ایمیل


                // فیلد شماره تلفن

                item {

                    TextField(
                        value = password,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                            cursorColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible) R.drawable.un_visibility else R.drawable.visibility
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = painterResource(image), contentDescription = if (passwordVisible) "Hide password" else "Show password")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .focusRequester(passwordFocusRequester), // FocusRequester برای پسورد
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // اکشن صفحه کلید به عنوان "Done"
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // انجام عملیات ثبت نام
                                errorMessage = validateInput(firstName.text, lastName.text, email.text,   password.text)
                                if (errorMessage.isEmpty()) {
                                    isLoading?.let {
                                         if (!it)
                                            userViewModel.register(
                                                firstName.text.trim(),
                                                lastName.text.trim(),
                                                password.text.trim(),
                                                email.text.trim()
                                            )
                                    }
                                }
                            }
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp, // میزان برجستگی در حالت عادی
                            pressedElevation = 8.dp
                        ),
                        onClick = {

                            errorMessage = validateInput(firstName.text, lastName.text, email.text,   password.text)

                            if (errorMessage.isEmpty()) {
                                isLoading?.let {
                                    if (!it)
                                        userViewModel.register(
                                            firstName.text.trim(),
                                            lastName.text.trim(),
                                            password.text.trim(),
                                            email.text.trim()
                                        )
                                }
                            }

                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)

                    ) {
                        Text("Register")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )

                    }
                }

                // فیلد پسورد
                item {

                    error?.let {
                        Text(
                            text = it, modifier = Modifier.fillMaxWidth(), fontSize = 12.sp,
                            textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.error, style = yekanBakhTextStyle
                        )
                    }
                }
                item {

                    Text(
                        text = "شرایط حریم خصوصی را می‌پذیرم.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Blue, // اضافه کردن رنگ آبی به متن
                        textDecoration = TextDecoration.Underline, // اضافه کردن زیرخط
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("privacy_policy_screen") // ناوبری به صفحه سیاست حریم خصوصی
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {

                    Text(
                        text = "در صورت ثبت نام هم زمان در برنامه زیر از این اکانت میتوانید استفاده کنید و یک کیف پول مشترک برای شما ایجاد میشود",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }


                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3), // تعداد ستون‌ها
                            modifier = Modifier.fillMaxSize() // پر کردن فضای موجود
                        ) {
                            items(filteredAppList) { app ->
                                AppItem(app, context) // آیتم اپلیکیشن
                            }
                        }
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }

            }


        }
    }
}


fun validateInput(firstName: String, lastName: String, email: String, password: String): String {

    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()  || password.isEmpty()) {

        return "همه فیلد ها لازم هستن"

    }

    val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    if (!emailPattern.matcher(email).matches()) {

        return "ایمیل صحیح نیست"

    }

    val phonePattern = Pattern.compile("^09[0-9]{9}$")



    if (password.length < 4) {
        return "پسورد حداقل 4 کاراکتر"

    }
    return ""

}


