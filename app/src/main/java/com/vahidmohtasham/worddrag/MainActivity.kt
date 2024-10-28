package com.vahidmohtasham.worddrag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vahidmohtasham.worddrag.ui.page.CategoryDifficultyScreen
import com.vahidmohtasham.worddrag.ui.theme.WordDragTheme
 // ViewModel to handle fetching data from API and managing UI state
@Composable
fun MyApp() {
    val navController = rememberNavController()



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
                }
            )
        }
        composable("category/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Unknown"
            CategoryDifficultyScreen(navController,
                category = category,
                onDifficultySelected = { difficulty ->
                    navController.navigate("game/${difficulty.name}") {
                        popUpTo("category/$category") { inclusive = true }
                    }
                }
            )
        }
        composable("game/{difficulty}") { backStackEntry ->
            val difficulty = Difficulty.valueOf(backStackEntry.arguments?.getString("difficulty") ?: "MEDIUM")
            LetterGameScreen(navController,difficulty)
        }
    }

}


// Main Activity
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




