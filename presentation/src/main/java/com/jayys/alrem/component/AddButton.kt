package com.jayys.alrem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddButton(modifier: Modifier, text: String, screenHeight: Dp, navigate: () -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(screenHeight * 0.08f)
        .padding(start = 20.dp, end = 20.dp, top = 2.dp)
        .clickable { navigate() }
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0.95f, 0.62f, 0.99f, 0.9f),
                    Color(0.53f, 0.44f, 0.99f, 0.9f)
                )
            ),
            shape = RoundedCornerShape(8.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}