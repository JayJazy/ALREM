package com.jayys.alrem.screen.rem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.alrem.R
import com.jayys.alrem.screen.rem.draw.DrawBar
import com.jayys.alrem.screen.rem.draw.DrawBarChartDataRange
import com.jayys.alrem.screen.rem.draw.DrawBarChartGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RemBarChartLayout(
    maxHeight: Dp,
    pagerState: PagerState,
    scaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {

    val maxChartHeight = maxHeight * 0.65f

    val randomTime = listOf(30240, 19080, 27420, 34320, 15120)

    val isRemPage by remember { derivedStateOf { pagerState.currentPage == 1 } }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight * 0.25f)
                .padding(end = 12.dp),
                contentAlignment = Alignment.CenterEnd)
            {
                IconButton(onClick = { coroutineScope.launch { scaffoldState.bottomSheetState.expand() } }) {
                    Icon(painter = painterResource(id = R.drawable.detail_icon), contentDescription = "자세히", tint = Color.White)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
                    .height(maxChartHeight))
                {
                    DrawBarChartDataRange(maxChartHeight)
                }


                Box(modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth()
                    .height(maxChartHeight))
                {
                    DrawBarChartGrid(maxChartHeight)
                    DrawBar(randomTime, maxChartHeight, isRemPage)
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight * 0.1f)){
                Box(modifier = Modifier.weight(0.1f))
                Box(modifier = Modifier.weight(0.9f)){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "12.18", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "12.18", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "12.18", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "12.18", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "12.18", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

        }

    }
}