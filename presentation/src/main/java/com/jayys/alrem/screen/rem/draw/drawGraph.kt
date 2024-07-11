package com.jayys.alrem.screen.rem.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.jayys.alrem.entity.RemEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun drawGraph(
    constraints: Constraints,
    remData: List<RemEntity>,
    currentYear: Int,
    currentMonth: Int
) : Long
{
    val sleepingTimesList = remData.filter {
        it.remDate.year == currentYear && it.remDate.monthValue == currentMonth
    }.map { it.sleepingTime }

    val selectedDate = remData.filter {
        it.remDate.year == currentYear && it.remDate.monthValue == currentMonth
    }.map { it.remDate }


    val pressPosition = remember { mutableStateOf<Offset?>(null) }
    var sleepingTime by remember { mutableDoubleStateOf(0.0) }
    var wakeUpDate by remember { mutableStateOf(LocalDateTime.now()) }

    val timeInDecimals = sleepingTimesList.map { seconds ->
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val decimalTime = hours + minutes / 100.0
            String.format("%.2f", decimalTime).toDouble()
    }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(start = 6.dp, end = 16.dp)
        .pointerInput(Unit) {
            coroutineScope {
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
        val interval = width / 30

        val limitedTimeInDecimals = timeInDecimals.take(31)

        val positionMap = when (limitedTimeInDecimals.size) {
            in 2..31 -> {
                limitedTimeInDecimals.mapIndexed { index, value ->
                    val x = interval * index
                    val y = height - (height * (value / 15.0))
                    x to y
                }
            }
            1 -> {
                val value = limitedTimeInDecimals.firstOrNull() ?: 0.0
                val y = height - (height * (value / 15.0))
                listOf(width / 30 * 1 to y)
            }
            else -> {
                emptyList()
            }
        }

        // 그래프 그리기
        positionMap.zipWithNext{ x, y ->
            drawLine(
                color = Color(0xFF9F3EFF),
                start = Offset(x.first, x.second.toFloat()),
                end = Offset(y.first, y.second.toFloat()),
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
                    color = Color.Gray,
                    start = Offset(it, -100f),
                    end = Offset(it, height + 100f),
                    strokeWidth = 3f
                )
            }

            // dotPosition 정보 저장
            val nearestPointIndex = positionMap.minByOrNull { abs(it.first - press.x) }?. let{
                positionMap.indexOf(it)
            }
            nearestPointIndex?.let { index ->
                sleepingTime = limitedTimeInDecimals[index]
                wakeUpDate = selectedDate[index]
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
            clickXInDp + 10.dp
        }

        val boxHeight = 150.dp
        val canvasHeight = 250.dp
        val yOffset = (canvasHeight - boxHeight) / 2


        // 정보 표시 Box
        if(sleepingTimesList.isNotEmpty()){
            val height = 160.dp
            Box(
                modifier = Modifier
                    .height(height).width(180.dp)
                    .offset(x = boxOffsetX, y = yOffset)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF212327)),
                contentAlignment = Alignment.Center
            ) {
                val sleepingTimeText = sleepingTimeToText(sleepingTime)
                val bedTime = bedTimeToText(sleepingTime, wakeUpDate)

                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(height * 0.08f))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(text = "${wakeUpDate.year}년 ${wakeUpDate.monthValue}월 ${wakeUpDate.dayOfMonth}일 (${mappingDayOfWeek(wakeUpDate.dayOfWeek)})",
                            color = Color.White,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(height * 0.13f))


                    val bedTimeHour = if(bedTime.hour > 12) "오후 ${bedTime.hour - 12}시"
                    else if (bedTime.hour == 12) "오후 ${bedTime.hour}시"
                    else "오전 ${bedTime.hour}시"

                    val bedTimeMin = if (bedTime.minute == 0) "" else "${bedTime.minute}분"
                    Text(text = "취침 시간 : $bedTimeHour $bedTimeMin", color = Color.White, fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp))


                    Spacer(modifier = Modifier.height(height * 0.08f))
                    val wakeUpTimeHour = if(wakeUpDate.hour > 12) "오후 ${wakeUpDate.hour - 12}시"
                    else if (wakeUpDate.hour == 12) "오후 ${wakeUpDate.hour}시"
                    else "오전 ${wakeUpDate.hour}시"

                    val wakeUpTimeMin = if (wakeUpDate.minute == 0) "" else "${wakeUpDate.minute}분"
                    Text(text = "기상 시간 : $wakeUpTimeHour $wakeUpTimeMin", color = Color.White, fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp))

                    Spacer(modifier = Modifier.height(height * 0.08f))
                    Text(text = "수면 시간 : $sleepingTimeText", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp))
                }

            }
        }

    }

    return sleepingTimesList.average().toLong()
}

fun bedTimeToText(sleepingTime: Double, wakeUpDate: LocalDateTime): LocalDateTime {
    val hours = sleepingTime.toInt().toLong()
    val minutes = ((sleepingTime - hours) * 100).roundToInt().toLong()

    val duration = Duration.ofHours(hours).plusMinutes(minutes)
    return wakeUpDate.minus(duration)
}

fun sleepingTimeToText(sleepingTime: Double): String {
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

    return sleepingTimeText
}


fun mappingDayOfWeek(dayOfWeek: DayOfWeek): String {
    val shortDaysOfWeek = mapOf(
        DayOfWeek.MONDAY to "월",
        DayOfWeek.TUESDAY to "화",
        DayOfWeek.WEDNESDAY to "수",
        DayOfWeek.THURSDAY to "목",
        DayOfWeek.FRIDAY to "금",
        DayOfWeek.SATURDAY to "토",
        DayOfWeek.SUNDAY to "일"
    )
    return shortDaysOfWeek[dayOfWeek].toString()
}