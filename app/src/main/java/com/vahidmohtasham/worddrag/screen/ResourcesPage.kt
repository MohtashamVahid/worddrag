package com.vahidmohtasham.worddrag.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesPage(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Resources") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { p ->
        Column(modifier = Modifier
            .padding(p)
            .padding(16.dp)) {
            Text(
                text = "This application uses OpenAI models for content processing and generation.",

                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Models used: GPT-3 and GPT-4.",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "For more information about OpenAI, visit the official website: https://openai.com",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
