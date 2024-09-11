package com.vahidmohtasham.worddrag

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahidmohtasham.worddrag.ui.theme.WordDragTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.roundToInt
import kotlin.random.Random

// ViewModel to handle fetching data from API and managing UI state


// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordDragTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LetterGameScreen(Difficulty.MEDIUM) // Set difficulty level here (EASY, MEDIUM, HARD)
                }
            }
        }
    }
}

@Composable
fun LetterGameScreen(difficulty: Difficulty, viewModel: LetterGameViewModel = LetterGameViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWords()
    }

    when {
        state.isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        state.error != null -> Text("Error: ${state.error}", modifier = Modifier.fillMaxSize())
        else -> {
            val gridSize = 10 // Define grid size, e.g., 10x10 grid
            if (state.words.isNotEmpty()) {
                val grid = remember { generateGrid(gridSize, state.words, difficulty) }
                LettersTable(grid, state.words)
            }
        }
    }
}

fun generateGrid(gridSize: Int, words: List<String>, difficulty: Difficulty): List<CharArray> {
    val grid = Array(gridSize) { CharArray(gridSize) { ' ' } }

    // درج کلمات هدف در جدول
    for (word in words) {
        var placed = false
        while (!placed) {
            val direction = when (difficulty) {
                Difficulty.EASY -> Random.nextInt(2) // 0 برای افقی، 1 برای عمودی
                Difficulty.MEDIUM -> Random.nextInt(4) // اضافه شدن جهت‌های معکوس
                Difficulty.HARD -> Random.nextInt(8) // شامل جهت‌های مورب
            }

            when (direction) {
                0 -> { // چپ به راست
                    val row = Random.nextInt(gridSize)
                    val col = Random.nextInt(gridSize - word.length)
                    if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row][col + index] = c
                        }
                        placed = true
                    }
                }

                1 -> { // بالا به پایین
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize)
                    if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row + index][col] = c
                        }
                        placed = true
                    }
                }

                2 -> { // راست به چپ
                    val row = Random.nextInt(gridSize)
                    val col = Random.nextInt(gridSize - word.length)
                    if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row][col + index] = c
                        }
                        placed = true
                    }
                }

                3 -> { // پایین به بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize)
                    if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col] = c
                        }
                        placed = true
                    }
                }
                // سایر جهت‌های مورب برای سطح سخت
                4 -> { // مورب چپ-بالا به راست-پایین
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row + index][col + index] = c
                        }
                        placed = true
                    }
                }

                5 -> { // مورب راست-پایین به چپ-بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col + index] = c
                        }
                        placed = true
                    }
                }

                6 -> { // مورب راست-بالا به چپ-پایین
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row + index][col + index] = c
                        }
                        placed = true
                    }
                }

                7 -> { // مورب چپ-پایین به راست-بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col + index] = c
                        }
                        placed = true
                    }
                }
            }
        }
    }

    // پر کردن خانه‌های خالی با حروف تصادفی
    for (i in 0 until gridSize) {
        for (j in 0 until gridSize) {
            if (grid[i][j] == ' ') {
                grid[i][j] = ('A'..'Z').random()
            }
        }
    }

    return grid.toList()
}



@Composable
fun LettersTable(
    grid: List<CharArray>,
    targetWords: List<String>,
    cellSize: Dp = 40.dp,
    spacing: Dp = 4.dp // فاصله بین خانه‌ها
) {
    val columns = grid.size
    val rows = grid.size
    val cellSizePx = with(LocalDensity.current) { cellSize.toPx() }
    val spacingPx = with(LocalDensity.current) { spacing.toPx() }

    // States to track dragging
    var draggedLetters by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }
    var currentWord by remember { mutableStateOf("") }
    var foundWords by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }

    // Handle dragging within grid cells
    fun onDrag(row: Int, col: Int) {
        if (row in 0 until rows && col in 0 until columns) {
            val newPair = Pair(row, col)

            if (!draggedLetters.contains(newPair)) {
                draggedLetters = draggedLetters + newPair
                currentWord += grid[row][col]
            }
        }
    }

    // Handle the end of the drag action
    fun onDragEnd() {
        if (targetWords.contains(currentWord)) {
            foundWords = foundWords + draggedLetters
        }
        draggedLetters = emptyList()
        currentWord = ""
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val boxWidth = constraints.maxWidth
        val boxHeight = constraints.maxHeight

        val gridWidthPx = (columns * cellSizePx) + ((columns - 1) * spacingPx)
        val gridHeightPx = (rows * cellSizePx) + ((rows - 1) * spacingPx)

        val offsetX = (boxWidth - gridWidthPx) / 2
        val offsetY = (boxHeight - gridHeightPx) / 2

        // Convert absolute drag positions to grid coordinates
        fun positionToGridCoordinates(x: Float, y: Float): Pair<Int, Int> {
            val col = ((x - offsetX) / (cellSizePx + spacingPx)).roundToInt()
            val row = ((y - offsetY) / (cellSizePx + spacingPx)).roundToInt()
            return Pair(row, col)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // بک گراند صفحه سفید
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val (startRow, startCol) = positionToGridCoordinates(offset.x, offset.y)
                            onDrag(startRow, startCol)
                        },
                        onDragEnd = {
                            onDragEnd()
                        },
                        onDrag = { change, _ ->
                            val (draggedRow, draggedCol) = positionToGridCoordinates(change.position.x, change.position.y)
                            onDrag(draggedRow, draggedCol)
                        }
                    )
                }
        ) {
            // Draw the grid using Canvas
            Canvas(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(gridWidthPx.toDp(), gridHeightPx.toDp())
            ) {
                for (row in 0 until rows) {
                    for (col in 0 until columns) {
                        val letter = grid[row][col]
                        val isDragged = draggedLetters.contains(Pair(row, col))
                        val isFound = foundWords.contains(Pair(row, col))

                        val backgroundColor = when {
                            isFound -> Color.Green
                            isDragged -> Color.Red
                            else -> Color.LightGray // رنگ پیش‌فرض باکس‌ها
                        }

                        // Draw the cell background
                        drawRect(
                            color = backgroundColor,
                            topLeft = Offset(
                                col * (cellSizePx + spacingPx),
                                row * (cellSizePx + spacingPx)
                            ),
                            size = Size(cellSizePx, cellSizePx),
                            style = androidx.compose.ui.graphics.drawscope.Fill
                        )

                        drawIntoCanvas { canvas ->
                            canvas.nativeCanvas.drawText(
                                letter.toString(),
                                col * (cellSizePx + spacingPx) + cellSizePx / 2,
                                row * (cellSizePx + spacingPx) + cellSizePx / 2 - (cellSizePx / 2) * 0.3f,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = 40f // Adjust text size as needed
                                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


