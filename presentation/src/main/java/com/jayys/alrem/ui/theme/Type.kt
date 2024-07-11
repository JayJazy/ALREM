package com.jayys.alrem.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R


val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.taeback)),
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.gwangwon_gyoyook_light)),
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.gwangwon_gyoyook_bold)),
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp
    ),
)