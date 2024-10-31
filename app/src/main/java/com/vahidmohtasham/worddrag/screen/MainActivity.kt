package com.vahidmohtasham.worddrag.screen


// ViewModel to handle fetching data from API and managing UI state
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vahidmohtasham.worddrag.UserViewModelFactory
import com.vahidmohtasham.worddrag.api.RetrofitInstance
import com.vahidmohtasham.worddrag.api.UserRepository
import com.vahidmohtasham.worddrag.api.UserViewModel
import com.vahidmohtasham.worddrag.screen.category.CategoryDifficultyScreen
import com.vahidmohtasham.worddrag.screen.game.Difficulty
import com.vahidmohtasham.worddrag.screen.game.LetterGameScreen
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


    val viewModel = ViewModelProvider(context as MainActivity, UserViewModelFactory(UserRepository(context, apiService)))[UserViewModel::class.java]

    val loginResponse by viewModel.loginResponse.observeAsState()

    LaunchedEffect(Unit) {
//        val userId = viewModel.getUserId()
//
//        if (userId == null || viewModel.isTokenExpired()) {
//            val uniqueCode = viewModel.getUniqueID()
//            if (uniqueCode != null)
//                viewModel.loginGuest(uniqueCode)
//
//        } else {
        viewModel.fetchCategories()
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
                userName = "Adventurer",
                score = 75,
                level = "Intermediate",
                onCategorySelected = { category ->
                    navController.navigate("category/$category") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onDifficultySelected = { difficulty ->
                    navController.navigate("game/${difficulty.name}") {
                        popUpTo("profile") { inclusive = true }
                    }
                }, userViewModel = viewModel
            )
        }
        composable("category/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            CategoryDifficultyScreen(
                navController,
                category = category,
                onDifficultySelected = { difficulty ->
                    navController.navigate("game/${difficulty.name}") {
                        popUpTo("category/$category") { inclusive = true }
                    }
                }, viewModel
            )
        }
        composable("game/{difficulty}") { backStackEntry ->
            val difficulty = Difficulty.valueOf(backStackEntry.arguments?.getString("difficulty") ?: "MEDIUM")
            LetterGameScreen(navController, difficulty)
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




