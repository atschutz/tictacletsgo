package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TTTBox(modifier: Modifier = Modifier, symbol: String = " ", onClick: () -> Unit) {
    Text(
        text = symbol,
        modifier = modifier
            .aspectRatio(1F)
            .background(Color.LightGray)
            .border(width = 1.dp, color = Color.Black)
            .clickable { onClick() }
    )
}