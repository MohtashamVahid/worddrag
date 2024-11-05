package com.vahidmohtasham.worddrag.screen.learned

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vahidmohtasham.worddrag.api.responses.CompleteStageRequest
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnWordsScreen(
    navController: NavHostController,
    learnedWordsViewModel: LearnedWordsViewModel,
    stageId: String,
    progressViewModel: ProgressViewModel
) {
    val selectedWords by learnedWordsViewModel.selectedWords.collectAsState()
    val startStageResponse by progressViewModel.startStageResponse.observeAsState()
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
                actions = { // اضافه کردن دکمه به بخش actions
                    IconButton(
                        onClick = {
                            startStageResponse?.let {
                                navController.navigate("learn_words_screen/${it.stage?.id}")
                            }
                        },
                        // enabled = isButtonEnabled // فعال یا غیرفعال کردن دکمه
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "What word did you learn?", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                startStageResponse?.stage?.let { stage ->
                    LazyColumn {
                        items(stage.words) { word ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = selectedWords.contains(word),
                                    onCheckedChange = {
                                        learnedWordsViewModel.toggleWordSelection(word)
                                    }
                                )
                                Text(text = word.wordId.word, fontSize = 18.sp) // استفاده از متن کلمه
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        learnedWordsViewModel.submitLearnedWords(stageId)
                        startStageResponse?.stage?.let { stage ->
                            if (selectedWords.size == stage.words.size) {
                                val completeStageRequest = CompleteStageRequest( stageId)
                                learnedWordsViewModel.completeStage(completeStageRequest) {
                                        progressViewModel.getUserProgress()

                                }
                            }
                        }
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Submit Learned Words")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Please select the words you want to remove. Checked words will be sent to the server and will not appear in future stages of the game.",
                    fontSize = 12.sp
                )

            }
        }
    )
}
