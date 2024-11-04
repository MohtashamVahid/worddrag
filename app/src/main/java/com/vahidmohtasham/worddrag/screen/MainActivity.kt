package com.vahidmohtasham.worddrag.screen


// ViewModel to handle fetching data from API and managing UI state
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adivery.sdk.Adivery
import com.adivery.sdk.AdiveryListener
import com.vahidmohtasham.worddrag.BuildConfig
import com.vahidmohtasham.worddrag.screen.user.UserViewModelFactory
import com.vahidmohtasham.worddrag.api.RetrofitInstance
import com.vahidmohtasham.worddrag.api.responses.StartNewStageRequest
import com.vahidmohtasham.worddrag.viewmodels.repositorys.UserRepository
import com.vahidmohtasham.worddrag.viewmodels.UserViewModel
import com.vahidmohtasham.worddrag.screen.category.CategoryDifficultyScreen
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModel
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModelFactory
import com.vahidmohtasham.worddrag.screen.game.Difficulty
import com.vahidmohtasham.worddrag.screen.game.LetterGameScreen
import com.vahidmohtasham.worddrag.screen.learned.LearnWordsScreen
import com.vahidmohtasham.worddrag.screen.learned.LearnedWordsViewModel
import com.vahidmohtasham.worddrag.screen.learned.LearnedWordsViewModelFactory
import com.vahidmohtasham.worddrag.screen.login.EmailVerificationScreen
import com.vahidmohtasham.worddrag.screen.login.LoginWithEmailScreen
import com.vahidmohtasham.worddrag.screen.login.PrivacyPolicyScreen
import com.vahidmohtasham.worddrag.screen.login.ResetPasswordScreen
import com.vahidmohtasham.worddrag.screen.login.SignUpScreen
import com.vahidmohtasham.worddrag.screen.user.UserProfileScreen
import com.vahidmohtasham.worddrag.ui.theme.WordDragTheme
import com.vahidmohtasham.worddrag.utils.CheckAppUpdateScreen
import okhttp3.logging.HttpLoggingInterceptor


@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current


    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
    }


    val userViewModel =
        ViewModelProvider(context as MainActivity, UserViewModelFactory(UserRepository(context)))[UserViewModel::class.java]

    val progressApi = userViewModel.getProgressApi(context)

    getApplication()?.let {
        initAdivery(it, userViewModel)
    }

    val loginResponse by userViewModel.loginResponse.observeAsState()


    val progressViewModel: ProgressViewModel = viewModel(factory = ProgressViewModelFactory(progressApi))
    val learnedWordsViewModel: LearnedWordsViewModel =
        ViewModelProvider(context, LearnedWordsViewModelFactory(progressApi)).get(LearnedWordsViewModel::class.java)

    val userProgressResponse by progressViewModel.userProgressResponse.observeAsState()
    val startStageResponse by progressViewModel.startStageResponse.observeAsState()



    LaunchedEffect(loginResponse) {
        loginResponse?.let {
            userViewModel.updateApiServices(context)
        }
    }

    LaunchedEffect(Unit) {
        userViewModel.loadConfig()
        userViewModel.fetchCategories()

        val userId = userViewModel.getUserId()
        userId?.let {
            progressViewModel.getUserProgress(userId)
        }
    }



    CheckAppUpdateScreen(viewModel = userViewModel)

    LaunchedEffect(startStageResponse) {
        startStageResponse?.let {
            Log.d("vhdmht", "vhdmht")
        }
    }

    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                navController.navigate("profile") {
                    popUpTo("splash") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        composable("profile") {
            var totalLearnedWords = 0
            var currentLevelLabel = "beginner"
            userProgressResponse?.let {

                totalLearnedWords = it.totalLearnedWords // تعداد لغات یادگرفته شده
                currentLevelLabel = it.currentLevelLabel
                val requiredWords = it.requiredWords ?: 0 // تعداد لغات مورد نیاز برای سطح بعدی
                val progressPercentage = if (requiredWords > 0) {
                    (totalLearnedWords.toFloat() / (totalLearnedWords + requiredWords) * 100).toInt()
                } else {
                    100 // اگر کاربر در بالاترین سطح است
                }
            }

            UserProfileScreen(
                navController,
                userName = "Adventurer",
                score = totalLearnedWords,
                level = currentLevelLabel,
                onCategorySelected = { category ->
                    if (userViewModel.isTokenExpired(context)) {
                        navController.navigate("login_with_email_screen")
                    } else {
                        navController.navigate("category/${category.name}/${category.id}") {
                            popUpTo("profile") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                userViewModel = userViewModel
            )
        }


        composable("category/{categoryName}/{categoryId}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Unknown"
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""

            val userId = userViewModel.getUserId()
            if (!userId.isNullOrEmpty()) {
                val req = StartNewStageRequest(
                    userId = userId,
                    categoryId = categoryId,
                    wordsPerStage = 4
                )
                progressViewModel.startNewStage(req)
            }

            CategoryDifficultyScreen(
                navController,
                categoryName = categoryName,
                onDifficultySelected = { difficulty ->
                    navController.navigate("game/${difficulty.name}") {
                        popUpTo("category/$categoryName/${categoryId}") { inclusive = true }
                    }
                },
                userViewModel
            )

        }

        composable("game/{difficulty}") { backStackEntry ->
            val difficulty = remember { Difficulty.valueOf(backStackEntry.arguments?.getString("difficulty") ?: "MEDIUM") }
            LetterGameScreen(navController, difficulty, progressViewModel = progressViewModel)
        }


        composable("login_with_email_screen") {
            LoginWithEmailScreen(navController, userViewModel)
        }

        composable("reset_password_screen") {
            ResetPasswordScreen(navController, userViewModel)
        }

        composable("sign_up_screen") {
            SignUpScreen(navController, userViewModel)
        }

        composable("email_verification_screen") {
            EmailVerificationScreen(navController, userViewModel)
        }

        composable("privacy_policy_screen") {
            PrivacyPolicyScreen(navController)
        }

        composable("learn_words_screen/{stageId}") { backStackEntry ->
            val stageId = remember { backStackEntry.arguments?.getString("stageId") }

            val userId = userViewModel.getUserId()
            if (!userId.isNullOrEmpty()) {
                stageId?.let {
                    LearnWordsScreen(navController, learnedWordsViewModel, userId, stageId, progressViewModel = progressViewModel)
                }
            }
        }
    }

}


@Composable
fun getApplication(): Application? {
    val context = LocalContext.current
    return context.applicationContext as? Application
}


@Composable
private fun initAdivery(application: Application, userViewModel: UserViewModel) {

    Adivery.configure(application, "482f627b-c9a8-46d8-8f84-9abf603369ea");

    Adivery.prepareInterstitialAd(application, BuildConfig.BANNER_AD_ID_Interstitial);

    Adivery.addGlobalListener(object : AdiveryListener() {
        override fun onInterstitialAdClosed(placementId: String) {
            super.onInterstitialAdClosed(placementId)
            userViewModel.setRandomTimer(BuildConfig.BANNER_AD_ID_Interstitial)
        }

        override fun onAppOpenAdLoaded(placementId: String) {
            // تبلیغ اجرای اپلیکیشن بارگذاری شده است.
        }

        override fun onInterstitialAdLoaded(placementId: String) {
            // تبلیغ میان‌صفحه‌ای بارگذاری شده
        }

        override fun onRewardedAdLoaded(placementId: String) {
            // تبلیغ جایزه‌ای بارگذاری شده
        }

        override fun onRewardedAdClosed(placementId: String, isRewarded: Boolean) {
            // بررسی کنید که آیا کاربر جایزه دریافت می‌کند یا خیر
        }

        override fun log(placementId: String, log: String) {
            // پیغام را چاپ کنید
        }
    })

//        Adivery.setLoggingEnabled(true)
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)


        window.statusBarColor = Color.Transparent.toArgb() // مخفی کردن نوار وضعیت

        setContent {
            WordDragTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }

    fun resetStatusBar() {
        // بازگرداندن به حالت پیش‌فرض سیستم
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }

}




