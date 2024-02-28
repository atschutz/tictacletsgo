package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Board() {
    var boardSize by remember { mutableIntStateOf(3) }
    var boardState by remember { mutableStateOf(List(boardSize) { List(boardSize) { " " }})}
    var previousMove by remember { mutableStateOf(listOf<Int>()) }
    var isX by remember { mutableStateOf(true) }
    var winType by remember { mutableStateOf("") }
    var winSize by remember { mutableIntStateOf(3) }

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        boardState.forEachIndexed { rowIndex, row ->
            Row(
            ) {
                row.forEachIndexed { boxIndex, box ->
                    TTTBox(
                        modifier = Modifier.weight(1F),
                        symbol = box
                    ) {
                        val board = boardState.map { it.toMutableList() }

                        board[rowIndex][boxIndex] =
                            if (box == " ") {
                                isX = !isX
                                if (!isX) "X" else "O"
                            } else box

                        boardState = board
                        previousMove = listOf(boxIndex, rowIndex)
                        winType = getWinType(boardState, boxIndex, rowIndex, winSize)
                    }
                }
            }
        }
        if (winType.isNotEmpty()) Text(text = winType)

        Text(
            text = "Undo",
            modifier = Modifier
                .background(if (previousMove.isEmpty()) Color.DarkGray else Color.Green)
                .clickable {
                    if (previousMove.isNotEmpty()) {
                        val board = boardState.map { it.toMutableList() }

                        board[previousMove.last()][previousMove.first()] = " "

                        boardState = board
                        isX = !isX
                        previousMove = listOf()
                    }
                }
        )
        Text(
            text = "Restart",
            modifier = Modifier
                .background(Color.Red)
                .clickable {
                    boardState = List(boardSize) { List(boardSize) { " " }}
                    isX = true
                    previousMove = listOf()
                }
        )
        Text(
            text = "More Squares",
            modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    if (boardSize < 10) {
                        boardSize++
                        boardState = List(boardSize) { List(boardSize) { " " }}
                        isX = true
                        previousMove = listOf()
                    }
                }
        )
        Text(text = "$boardSize",)
        Text(
            text = "Less Squares",
            modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    if (boardSize > 1) {
                        boardSize--
                        boardState = List(boardSize) { List(boardSize) { " " }}
                        isX = true
                        previousMove = listOf()
                    }
                }
        )
        Text(
            text = "More to win",
            modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    if (winSize < boardSize) {
                        winSize++
                        boardState = List(boardSize) { List(boardSize) { " " }}
                        isX = true
                        previousMove = listOf()
                    }
                }
        )
        Text(text = "$winSize")
        Text(
            text = "Less to win",
            modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    if (winSize > 2) {
                        winSize--
                        boardState = List(boardSize) { List(boardSize) { " " }}
                        isX = true
                        previousMove = listOf()
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    Board()
}

fun getWinType(boardState: List<List<String>>, x: Int, y: Int, winSize: Int): String {
    // Check row
    var count = 1
    if (x > 0) {
        for (i in (x - 1).downTo(0)) {
            if (boardState[y][i] == boardState[y][x]) count++
            else break
        }
    }
    if (x < boardState.size) {
        for (i in (x + 1)..boardState.lastIndex) {
            if (boardState[y][i] == boardState[y][x]) count++
            else break
        }
    }
    if (count >= winSize) return boardState[y][x]

    // Check column
    count = 1
    if (y > 0) {
        for (i in (y - 1).downTo(0)) {
            if (boardState[i][x] == boardState[y][x]) count++
            else break
        }
    }
    if (y < boardState.size) {
        for (i in (y + 1)..boardState.lastIndex) {
            if (boardState[i][x] == boardState[y][x]) count++
            else break
        }
    }
    if (count >= winSize) return boardState[y][x]

    // Check left-to-right diagonal
    count = 1
    var i = x
    var j = y

    while (i > 0 && j > 0) {
        i--
        j--
        if (boardState[j][i] == boardState[y][x])  count++
        else break
    }

    i = x
    j = y

    while (i < boardState.lastIndex && j < boardState.lastIndex) {
        i++
        j++
        if (boardState[j][i] == boardState[y][x])  count++
        else break
    }
    if (count >= winSize) return boardState[y][x]

    // Check right-to-left diagonal
    count = 1
    i = x
    j = y

    while (i > 0 && j < boardState.lastIndex) {
        i--
        j++
        if (boardState[j][i] == boardState[y][x]) count++
        else break
    }
    i = x
    j = y
    while (i < boardState.lastIndex && j > 0) {
        i++
        j--
        if (boardState[j][i] == boardState[y][x]) count++
        else break
    }
    if (count >= winSize) return boardState[y][x]

    return ""
}