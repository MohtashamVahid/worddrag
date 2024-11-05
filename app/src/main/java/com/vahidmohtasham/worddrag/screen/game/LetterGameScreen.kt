package com.vahidmohtasham.worddrag.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.BannerAdCardView
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModel
import com.vahidmohtasham.worddrag.screen.category.WordData
import com.vahidmohtasham.worddrag.screen.category.WordDetails
import com.vahidmohtasham.worddrag.ui.theme.yekanBakhTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterGameScreen(
    navController: NavHostController,
    difficulty: Difficulty,
    progressViewModel: ProgressViewModel
) {
    val startStageResponse by progressViewModel.startStageResponse.observeAsState()
    val isLoading by progressViewModel.isLoading.observeAsState()
    val error by progressViewModel.error.observeAsState()
    val viewModel: LetterGameViewModel = viewModel()

    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState() // وضعیت دکمه

    LaunchedEffect(Unit) {
        startStageResponse?.let {
            it.stage?.let { stage ->
                if (stage.words.isNotEmpty()) {
                    viewModel.loadWords(stage.words)
                }
            }
        }
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
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showDialog=true
                        }
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "Info")
                    }

                    IconButton(
                        onClick = {
                            startStageResponse?.let {
                                navController.navigate("learn_words_screen/${it.stage?.id}")
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = "Check")
                    }


                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val gridSize = when {
                    maxWidth < 600.dp -> 6 // برای دستگاه‌های کوچک
                    maxWidth < 900.dp -> 8 // برای دستگاه‌های متوسط
                    else -> 10 // برای دستگاه‌های بزرگ
                }

                when {
                    state.isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    state.error != null -> Text("Error: ${state.error}", modifier = Modifier.fillMaxSize())
                    else -> {
                        if (state.words.isNotEmpty()) {
                            val grid = remember { generateGrid(gridSize, state.words, difficulty) }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                startStageResponse?.let {
                                    it.stage?.let { stage ->
                                        WordHint(stage.words)
                                    }
                                }

                                BannerAdCardView(
                                    "7fa22e57-68c4-4157-85a0-485f7fa08f25",
                                    Modifier.padding(top = 16.dp, end = 8.dp, start = 8.dp, bottom = 16.dp)
                                )
                                LettersTable(grid, state.words, viewModel = viewModel) // ارسال ViewModel به LettersTable


                            }
                        } else {
                            Text("No words available", modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            DirectionsScreen(
                difficulty = difficulty,
                showDialog = showDialog,
                onDismiss = { showDialog = false }
            )

        }
    )
}

@Composable
fun WordHint(wordDataList: List<WordData>) {
    var currentIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // دکمه تغییر به کلمه قبلی
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

            // نمایش کلمه
            Text(
                text = "Word: ${wordDataList[currentIndex].wordId.word}",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 18.sp,
                color = Color.Black,

                )

            // دکمه تغییر به کلمه بعدی
            IconButton(onClick = {
                if (currentIndex < wordDataList.size - 1) {
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

        // نمایش معنی کلمه
        Text(
            text = wordDataList[currentIndex].wordId.meaning,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 16.sp,
            color = Color.Gray, style = yekanBakhTextStyle
        )

        // نمایش مثال اگر وجود دارد
        wordDataList[currentIndex].wordId.example?.let { example ->
            Text(
                text = "Example: $example",
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 14.sp,
                color = Color.DarkGray,
            )
        }

        // نمایش وضعیت یادگیری کلمه
        val learnedText = if (wordDataList[currentIndex].learned) "Learned" else "Not Learned"
        Text(
            text = "Status: $learnedText",
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 14.sp,
            color = if (wordDataList[currentIndex].learned) Color.Green else Color.Red
        )
    }
}






