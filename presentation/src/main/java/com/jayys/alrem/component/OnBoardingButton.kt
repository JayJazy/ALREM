package com.jayys.alrem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingButton(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    text: String,
    onFinish: () -> Unit
)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(horizontal = 40.dp).padding(vertical = 5.dp)
        .clip(RoundedCornerShape(6.dp))
        .background(Color(0xFF51C0FF))
        .clickable {
            if(text == "시작하기")
            {
                onFinish()
            }
            else{
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }, contentAlignment = Alignment.Center)
    {
        Text(text = text, color = Color.White)
    }
}