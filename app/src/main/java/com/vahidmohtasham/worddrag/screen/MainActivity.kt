package com.vahidmohtasham.worddrag.screen


// ViewModel to handle fetching data from API and managing UI state
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
import com.vahidmohtasham.worddrag.UserViewModelFactory
import com.vahidmohtasham.worddrag.api.RetrofitInstance
import com.vahidmohtasham.worddrag.api.StartNewStageRequest
import com.vahidmohtasham.worddrag.api.UserRepository
import com.vahidmohtasham.worddrag.api.UserViewModel
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
import com.vahidmohtasham.worddrag.ui.theme.WordDragTheme
import okhttp3.logging.HttpLoggingInterceptor


@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
    }


    val apiService = RetrofitInstance.getApiService(context)


    val userViewModel =
        ViewModelProvider(context as MainActivity, UserViewModelFactory(UserRepository(context, apiService)))[UserViewModel::class.java]

    val loginResponse by userViewModel.loginResponse.observeAsState()


    val progressApi = RetrofitInstance.getProgressApi(context)
    val progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModelFactory(progressApi)
    )

    val learnedWordsViewModel: LearnedWordsViewModel =
        ViewModelProvider(context, LearnedWordsViewModelFactory(progressApi)).get(LearnedWordsViewModel::class.java)

    val startStageResponse by progressViewModel.startStageResponse.observeAsState()


    LaunchedEffect(Unit) {
//        val userId = viewModel.getUserId()
//
//        if (userId == null || viewModel.isTokenExpired()) {
//            val uniqueCode = viewModel.getUniqueID()
//            if (uniqueCode != null)
//                viewModel.loginGuest(uniqueCode)
//
//        } else {
        userViewModel.fetchCategories()
//        }
//
    }

//    LaunchedEffect(loginResponse) {
//        loginResponse?.let {
//
//            val userId = viewModel.getUserId()
//            if (userId != null) {
//                viewModel.fetchCategories(userId)
//            }
//        }
//    }


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
                }
            }
        }
        composable("profile") {
            UserProfileScreen(
                navController,
                userName = "Adventurer",
                score = 75,
                level = "Intermediate",
                onCategorySelected = { category ->
                    if (userViewModel.isTokenExpired(context)) {
                        navController.navigate("login_with_email_screen")
                    } else {
                        navController.navigate("category/${category.name}/${category.id}") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }

                }, userViewModel = userViewModel
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
                }
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // تنظیم رنگ Status Bar
        window.statusBarColor = Color(0xFFB2DFDB).toArgb() // رنگ مورد نظ

        setContent {
            WordDragTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }
}




