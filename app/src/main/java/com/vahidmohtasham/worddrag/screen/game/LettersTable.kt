package com.vahidmohtasham.worddrag.screen.game

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.utils.toDp
import kotlin.math.max


import kotlin.math.roundToInt

import kotlin.random.Random

fun generateGrid(gridSize: Int, words: List<String>, difficulty: Difficulty): List<CharArray> {
    require(gridSize > 0) { "Grid size must be greater than 0." }

    val maxWordLength = words.map { it.length }.maxOrNull() ?: 0
    val effectiveGridSize = maxOf(gridSize, maxWordLength)
    val grid = Array(effectiveGridSize) { CharArray(effectiveGridSize) { ' ' } }

    for (word in words.map { it.trim().replace(" ", "") }) {
        var placed = false
        while (!placed) {
            try {
                val direction = when (difficulty) {
                    Difficulty.EASY -> Random.nextInt(2) // افقی و عمودی
                    Difficulty.MEDIUM -> Random.nextInt(4) // افقی، عمودی و دو جهت معکوس
                    Difficulty.HARD -> Random.nextInt(8) // شامل همه جهات
                }

                when (direction) {
                    0 -> { // چپ به راست
                        val row = Random.nextInt(effectiveGridSize)
                        val colBound = effectiveGridSize - word.length + 1
                        if (colBound > 0) { // بررسی مثبت بودن colBound
                            val col = Random.nextInt(colBound)
                            if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                                word.forEachIndexed { index, c ->
                                    grid[row][col + index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    1 -> { // بالا به پایین
                        val rowBound = effectiveGridSize - word.length + 1
                        val col = Random.nextInt(effectiveGridSize)
                        if (rowBound > 0) { // بررسی مثبت بودن rowBound
                            val row = Random.nextInt(rowBound)
                            if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                                word.forEachIndexed { index, c ->
                                    grid[row + index][col] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    2 -> { // راست به چپ
                        val row = Random.nextInt(effectiveGridSize)
                        val colBound = effectiveGridSize - word.length + 1
                        if (colBound > 0) {
                            val col = Random.nextInt(colBound)
                            if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                                word.reversed().forEachIndexed { index, c ->
                                    grid[row][col + index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    3 -> { // پایین به بالا
                        val rowBound = effectiveGridSize - word.length + 1
                        val col = Random.nextInt(effectiveGridSize)
                        if (rowBound > 0) {
                            val row = Random.nextInt(rowBound)
                            if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                                word.reversed().forEachIndexed { index, c ->
                                    grid[row + index][col] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    4 -> { // مورب چپ-بالا به راست-پایین
                        val rowBound = effectiveGridSize - word.length + 1
                        val colBound = effectiveGridSize - word.length + 1
                        if (rowBound > 0 && colBound > 0) {
                            val row = Random.nextInt(rowBound)
                            val col = Random.nextInt(colBound)
                            if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                                word.forEachIndexed { index, c ->
                                    grid[row + index][col + index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    5 -> { // مورب راست-پایین به چپ-بالا
                        val rowBound = effectiveGridSize - word.length + 1
                        val colBound = effectiveGridSize - word.length + 1
                        if (rowBound > 0 && colBound > 0) {
                            val row = Random.nextInt(rowBound)
                            val col = Random.nextInt(colBound)
                            if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                                word.reversed().forEachIndexed { index, c ->
                                    grid[row + index][col + index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    6 -> { // مورب راست-بالا به چپ-پایین
                        val rowBound = effectiveGridSize - word.length + 1
                        val col = Random.nextInt(effectiveGridSize)
                        if (rowBound > 0 && col >= word.length) {
                            val row = Random.nextInt(rowBound)
                            if ((0 until word.length).all { grid[row + it][col - it] == ' ' }) {
                                word.forEachIndexed { index, c ->
                                    grid[row + index][col - index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }

                    7 -> { // مورب چپ-پایین به راست-بالا
                        val rowBound = effectiveGridSize - word.length + 1
                        val col = Random.nextInt(effectiveGridSize)
                        if (rowBound > 0 && col < effectiveGridSize - word.length) {
                            val row = Random.nextInt(rowBound)
                            if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                                word.reversed().forEachIndexed { index, c ->
                                    grid[row + index][col + index] = c.uppercaseChar()
                                }
                                placed = true
                            }
                        }
                    }
                }
            } catch (e: ArrayIndexOutOfBoundsException) {
                // اگر خطا رخ داد، مجدداً تلاش شود
                continue
            }
        }
    }

    // پر کردن خانه‌های خالی با حروف تصادفی
    for (i in 0 until effectiveGridSize) {
        for (j in 0 until effectiveGridSize) {
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
    spacing: Dp = 4.dp, // فاصله بین خانه‌ها
    viewModel: LetterGameViewModel // پاس دادن ViewModel به کامپوزبل
) {
    val columns = grid.size
    val rows = grid.size
    val density = LocalDensity.current

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
        val lowercaseCurrentWord = currentWord.lowercase()

        if (targetWords.map { it.lowercase() }.contains(lowercaseCurrentWord)) {
            viewModel.addFoundWord(lowercaseCurrentWord) // استفاده از ViewModel برای افزودن کلمه پیدا شده
            Log.d("LettersTable", "Word found: $lowercaseCurrentWord") // لاگ وقتی کلمه پیدا شده است
            foundWords = foundWords + draggedLetters
        } else {
            Log.d("LettersTable", "Word not found: $lowercaseCurrentWord") // لاگ وقتی کلمه پیدا نشده است
        }

        draggedLetters = emptyList()
        currentWord = ""
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        // Calculate available space
        val boxWidth = constraints.maxWidth.toFloat()
        val boxHeight = constraints.maxHeight.toFloat()

        // Calculate cell size dynamically
        val cellSizePx = (minOf(boxWidth, boxHeight) - ((columns - 1) * with(density) { spacing.toPx() })) / columns
        val spacingPx = with(density) { spacing.toPx() }

        val gridWidthPx = (columns * cellSizePx) + ((columns - 1) * spacingPx)
        val gridHeightPx = (rows * cellSizePx) + ((rows - 1) * spacingPx)

        val offsetX = (boxWidth - gridWidthPx) / 2
        val offsetY = (boxHeight - gridHeightPx) / 2

        // Convert absolute drag positions to grid coordinates
        fun positionToGridCoordinates(x: Float, y: Float): Pair<Int, Int> {
            val col = ((x - offsetX) / (cellSizePx + spacingPx)).toInt()
            val row = ((y - offsetY) / (cellSizePx + spacingPx)).toInt()
            return Pair(row, col)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // بک گراند صفحه سفید
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
            val secondary = MaterialTheme.colorScheme.secondary
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
                            isFound -> Color.Green // سبز برای خانه‌هایی که یافت شده‌اند
                            isDragged -> Color.Red // قرمز برای خانه‌هایی که در حال کشیده شدن هستند
                            else -> secondary // رنگ پیش‌فرض برای خانه‌های عادی (برای هماهنگی با بک‌گراند)
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

                        // Use Compose to draw the letter with style
                        drawContext.canvas.nativeCanvas.apply {
                            val textPaint = android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = (cellSizePx * 0.6).toFloat() // تنظیم اندازه متن
                            }

                            val textBounds = android.graphics.Rect()
                            textPaint.getTextBounds(letter.toString(), 0, 1, textBounds)

                            // مرکز متن در سلول
                            val xPos = col * (cellSizePx + spacingPx) + (cellSizePx / 2)
                            val yPos = row * (cellSizePx + spacingPx) + (cellSizePx / 2) - (textBounds.exactCenterY())

                            drawText(letter.toString(), xPos, yPos, textPaint)
                        }
                    }
                }
            }
        }
    }
}

