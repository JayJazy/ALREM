package com.jayys.alrem.screen.rem.draw

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun drawGraph(constraints: Constraints) : Double
{
    val pressPosition = remember { mutableStateOf<Offset?>(null) }
    var sleepingTime by remember { mutableDoubleStateOf(0.0) }


    val randomNumbers = listOf(
        43200, 35640, 7140, 540, 60, 6060,
        9660, 14100, 38760, 31980, 47580,
        23760, 43440, 50820, 41340, 24180,
        41580, 33180, 40740, 14880, 40920,
        32040, 31680, 25620, 300, 13680,
        47700, 28560, 52920, 22500, 18240
    )

    val timeInDecimals = randomNumbers.map { seconds ->
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val decimalTime = hours + minutes / 100.0
        String.format("%.2f", decimalTime).toDouble()
    }

    Canvas(modifier = Modifier.fillMaxSize().padding(start = 6.dp, end = 16.dp).pointerInput(Unit){
        kotlinx.coroutines.coroutineScope {
            while (true) {
                var longPressDetected = false

                // Long press 감지
                val job = launch {
                    delay(300)
                    longPressDetected = true
                }

                // Long press 감지를 기다림
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val anyPressed = event.changes.any { it.pressed }

                        if (longPressDetected && anyPressed) {
                            // Long press 감지 -> 드래그 처리
                            event.changes.forEach { change ->
                                if (change.positionChanged()) {
                                    pressPosition.value = change.position
                                    change.consume()
                                }
                            }
                        }
                        if (!anyPressed) break
                    }
                }

                job.cancel()
                pressPosition.value = null
            }
        }
    }) {

        // 그래프 그리기 필요한 정보
        val width = size.width
        val height = size.height

        val maxPosition = timeInDecimals.maxOrNull() ?: 0.0
        val minPosition = timeInDecimals.minOrNull() ?: 0.0

        val positionMap = timeInDecimals.mapIndexed { index, value ->
            val x = (width / (timeInDecimals.size - 1)) * index
            val y = height - (height * (value - minPosition) / (maxPosition - minPosition))
            x to y
        }


        // 그래프 그리기
        positionMap.zipWithNext{ a, b ->
            drawLine(
                color = Color(0xFF9F3EFF),
                start = Offset(a.first, a.second.toFloat()),
                end = Offset(b.first, b.second.toFloat()),
                strokeWidth = 8f,
                cap = Stroke.DefaultCap
            )
        }


        // 그래프 꼭짓점 원
        positionMap.forEach { (x, y) ->
            drawCircle(
                color = Color.White,
                radius = 5f,
                center = Offset(x, y.toFloat())
            )
        }


        // 그래프 드래그 시 세로 줄
        pressPosition.value?.let { press ->
            val nearestX = positionMap.minByOrNull { abs(it.first - press.x) }?.first
            nearestX?.let {
                drawLine(
                    color = Color.Red,
                    start = Offset(it, -100f),
                    end = Offset(it, height + 100f),
                    strokeWidth = 3f
                )
            }

            // dotPosition의 정보 저장
            val nearestPointIndex = positionMap.minByOrNull { abs(it.first - press.x) }?. let{
                positionMap.indexOf(it)
            }
            nearestPointIndex?.let { index ->
                sleepingTime = timeInDecimals[index]
            }
        }
    }


    // 그래프 드래그 시 정보 표시
    pressPosition.value?.let {

        // 화면 절반 나누는 기준
        val boxWidthInPx = constraints.maxWidth.toFloat()
        val halfBoxWidthInPx = boxWidthInPx / 2

        val clickXInPx = it.x

        val clickXInDp = with(LocalDensity.current) { it.x.toDp() }

        // Box 위치 조정
        val boxOffsetX = if (clickXInPx > halfBoxWidthInPx) {
            clickXInDp - 160.dp
        } else {
            clickXInDp + 40.dp
        }

        val boxHeight = 150.dp
        val canvasHeight = 250.dp
        val yOffset = (canvasHeight - boxHeight) / 2


        // 정보 표시 Box
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = boxOffsetX, y = yOffset)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF212327)),
            contentAlignment = Alignment.Center
        ) {
            val hours = sleepingTime.toInt()
            val minutes = ((sleepingTime - hours) * 100).roundToInt()
            val sleepingTimeText = buildString {
                if (hours > 0) {
                    append("${hours}시간 ")
                }
                if (minutes > 0) {
                    append("${minutes}분 ")
                }
            }.trim()
            Text(text = "수면 시간 : $sleepingTimeText", color = Color.White, fontSize = 14.sp)
        }
    }


    return timeInDecimals.average()
}