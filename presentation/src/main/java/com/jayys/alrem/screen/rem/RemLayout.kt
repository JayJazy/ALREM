package com.jayys.alrem.screen.rem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.component.RemButton
import com.jayys.alrem.entity.RemEntity


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RemLayout(
    screenHeight: Dp,
    pagerState: PagerState,
    remError: String?,
    remData: List<RemEntity>,
    onNavigateToRemSelectScreen: (String) -> Unit
)
{
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var showText by remember { mutableStateOf(false) }



    LaunchedEffect(scaffoldState.bottomSheetState) {
        snapshotFlow { scaffoldState.bottomSheetState.currentValue }
            .collect { sheetValue ->
                showText = sheetValue == SheetValue.Expanded
            }
    }


    val sheetShape by remember {
        derivedStateOf {
            if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            } else {
                RectangleShape
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent =
        {
            Column(
                modifier = Modifier.fillMaxWidth().height(screenHeight * 0.66f)
            ) {
                if (remError != null) {
                    Text(text = "${remError}\n앱을 다시 시작해 주세요", fontSize = 20.sp, color = Color.White)
                }
                else
                {
                    RemGraphLayout(screenHeight, showText, remData)
                }

            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = sheetShape,
        sheetContainerColor = MaterialTheme.colorScheme.onBackground,
        content =
        {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.24f)
                    .background(MaterialTheme.colorScheme.background))
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(screenHeight * 0.24f * 0.1f))

                        RemButton(screenHeight, "sun", onNavigateToRemSelectScreen)

                        Spacer(modifier = Modifier.height(screenHeight * 0.24f * 0.15f))

                        RemButton(screenHeight, "moon", onNavigateToRemSelectScreen)

                    }
                }


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.42f)
                    .background(MaterialTheme.colorScheme.background)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(MaterialTheme.colorScheme.onBackground))
                {
                    val maxHeight = screenHeight * 0.42f
                    RemBarChartLayout(maxHeight, pagerState, scaffoldState, coroutineScope, remData)
                }
            }
        }
    )
}