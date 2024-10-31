package com.vahidmohtasham.worddrag.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterGameScreen(navController: NavHostController, difficulty: Difficulty, viewModel: LetterGameViewModel = LetterGameViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWords()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Word Puzzle") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    state.error != null -> Text("Error: ${state.error}", modifier = Modifier.fillMaxSize())
                    else -> {
                        val gridSize = 10
                        if (state.words.isNotEmpty()) {
                            val grid = remember { generateGrid(gridSize, state.words, difficulty) }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // جدول حروف
                                WordHint(state.words)
                                LettersTable(grid, state.words)

                                // نمایش امتیاز زیر جدول
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .background(Color.LightGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.money_bag),
                                        contentDescription = "Score Icon",
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Text(text = "Score: ${state.score}", fontSize = 18.sp)
                                }

                                // راهنمای حرکت بین کلمات

                            }
                        } else {
                            Text("No words available", modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun WordHint(words: List<String>) {
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // دکمه تغییر متن به چپ
            IconButton(onClick = {
                if (currentIndex > 0) {
                    currentIndex -= 1
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous",
                    tint = Color.Black
                )
            }

            // نمایش متن راهنما
            Text(
                text = "Hint: ${words[currentIndex]}",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
                color = Color.Black
            )

            // دکمه تغییر متن به راست
            IconButton(onClick = {
                if (currentIndex < words.size - 1) {
                    currentIndex += 1
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.Black
                )
            }
        }
    }
}






