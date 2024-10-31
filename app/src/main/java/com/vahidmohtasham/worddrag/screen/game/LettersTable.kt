package com.vahidmohtasham.worddrag.screen.game

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.vahidmohtasham.worddrag.R
import com.vahidmohtasham.worddrag.toDp


import kotlin.math.roundToInt
import kotlin.random.Random


fun generateGrid(gridSize: Int, words: List<String>, difficulty: Difficulty): List<CharArray> {
    val grid = Array(gridSize) { CharArray(gridSize) { ' ' } }

    // درج کلمات هدف در جدول
    for (word in words) {
        var placed = false
        while (!placed) {
            val direction = when (difficulty) {
                Difficulty.EASY -> Random.nextInt(1) // 0 برای افقی، 1 برای عمودی
                Difficulty.MEDIUM -> Random.nextInt(4) // اضافه شدن جهت‌های معکوس
                Difficulty.HARD -> Random.nextInt(8) // شامل جهت‌های مورب
            }

            when (direction) {
                0 -> { // چپ به راست
                    val row = Random.nextInt(gridSize)
                    val col = Random.nextInt(gridSize - word.length)
                    if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row][col + index] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                1 -> { // بالا به پایین
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize)
                    if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row + index][col] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                2 -> { // راست به چپ
                    val row = Random.nextInt(gridSize)
                    val col = Random.nextInt(gridSize - word.length)
                    if (grid[row].sliceArray(col until col + word.length).all { it == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row][col + index] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                3 -> { // پایین به بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize)
                    if ((row until row + word.length).all { grid[it][col] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col] = c.uppercaseChar()
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
                            grid[row + index][col + index] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                5 -> { // مورب راست-پایین به چپ-بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col + index] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                6 -> { // مورب راست-بالا به چپ-پایین
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.forEachIndexed { index, c ->
                            grid[row + index][col + index] = c.uppercaseChar()
                        }
                        placed = true
                    }
                }

                7 -> { // مورب چپ-پایین به راست-بالا
                    val row = Random.nextInt(gridSize - word.length)
                    val col = Random.nextInt(gridSize - word.length)
                    if ((0 until word.length).all { grid[row + it][col + it] == ' ' }) {
                        word.reversed().forEachIndexed { index, c ->
                            grid[row + index][col + index] = c.uppercaseChar()
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
            } else {
                grid[i][j].uppercaseChar()
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
    val context = LocalContext.current
     val customFont = FontFamily(Font(R.font.kavoon_regular))
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
            val secondary=MaterialTheme.colorScheme.secondary
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

//                            val typeface2 = Typeface.createFromAsset(context.assets, "fonts/kavoon_regular.ttf")
                            val typeface2 = ResourcesCompat.getFont(context, R.font.kavoon_regular)
                            val textPaint = android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = (cellSizePx * 0.6).toFloat() // تنظیم اندازه متن
                                typeface = typeface2
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
