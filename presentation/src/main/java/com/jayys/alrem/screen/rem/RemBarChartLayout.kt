package com.jayys.alrem.screen.rem

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.screen.rem.draw.DrawBar
import com.jayys.alrem.screen.rem.draw.DrawBarChartDataRange
import com.jayys.alrem.screen.rem.draw.DrawBarChartGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class CustomMonthDay(
    val month: Int,
    val day: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemBarChartLayout(
    maxHeight: Dp,
    pagerState: PagerState,
    scaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    remData: List<RemEntity>
) {

    val maxChartHeight = maxHeight * 0.65f
    val latestSleepingTime = remData.sortedByDescending { it.remDate }.take(5).map { it.sleepingTime }
    val latestDate = remData.sortedByDescending { it.remDate }.take(5).map { CustomMonthDay(it.remDate.monthValue, it.remDate.dayOfMonth) }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                   Text(text = "최근 5일 수면 기록", fontSize = 18.sp, color = Color.White,
                       modifier = Modifier.padding(start = 30.dp),
                       style = MaterialTheme.typography.bodySmall)

                    IconButton(onClick = { coroutineScope.launch { scaffoldState.bottomSheetState.expand() } }) {
                        Icon(painter = painterResource(id = R.drawable.detail_icon),
                            contentDescription = stringResource(id = R.string.description_more_rem_data),
                            tint = Color.White)
                    }
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
                    DrawBar(latestSleepingTime, maxChartHeight, isRemPage)
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
                        for(i in latestDate.size -1  downTo 0)
                        {
                            Text(
                                text = "${latestDate[i].month}.${latestDate[i].day}",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

        }

    }
}