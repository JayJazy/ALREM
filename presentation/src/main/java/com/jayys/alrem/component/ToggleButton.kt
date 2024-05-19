package com.jayys.alrem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

@Composable
fun ToggleButton(
    day: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        modifier = modifier.clip(CircleShape),
        contentPadding = PaddingValues()    // 내부 패딩 제거 하여 글자 중앙에 배치
    ) {
        Text(day, color = textColor, fontFamily = MaterialTheme.typography.bodyMedium.fontFamily)
    }
}