package com.vahidmohtasham.worddrag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                userName = "John Doe",
                score = 75,
                level = "Intermediate",
                onDifficultySelected = { difficulty ->
                    navController.navigate("game/${difficulty.name}") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }
        composable("game/{difficulty}") { backStackEntry ->
            val difficulty = Difficulty.valueOf(backStackEntry.arguments?.getString("difficulty") ?: "MEDIUM")
            LetterGameScreen(difficulty)
        }
    }
}


// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordDragTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }
}




