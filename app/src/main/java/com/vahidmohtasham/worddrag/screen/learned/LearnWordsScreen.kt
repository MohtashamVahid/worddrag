package com.vahidmohtasham.worddrag.screen.learned

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
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
import com.vahidmohtasham.worddrag.screen.category.ProgressViewModel

@Composable
fun LearnWordsScreen(
    navController: NavHostController,
    learnedWordsViewModel: LearnedWordsViewModel,
    userId: String,
    stageId: String,
    progressViewModel: ProgressViewModel
) {
    val selectedWords by learnedWordsViewModel.selectedWords.collectAsState()
    val startStageResponse by progressViewModel.startStageResponse.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Learned Words", fontSize = 24.sp, fontWeight = FontWeight.Bold)

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
                learnedWordsViewModel.submitLearnedWords(userId, stageId)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit Learned Words")
        }
    }
}
