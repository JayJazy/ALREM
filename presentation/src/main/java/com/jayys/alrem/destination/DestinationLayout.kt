package com.jayys.alrem.destination

import android.app.Activity
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.broadcastreceiver.notification.createRemNotification
import com.jayys.alrem.destination.dismiss.DismissAlarm
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.datastore.SaveSwitchUseCase
import com.jayys.alrem.usecase.datastore.SaveWakeUpTimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.system.exitProcess


@Composable
fun DestinationLayout(
    alarm: AlarmEntity,
    dismissAlarm: DismissAlarm,
    saveSwitchUseCase: SaveSwitchUseCase,
    saveWakeUpTimeUseCase: SaveWakeUpTimeUseCase,
)
{
    val context = LocalContext.current
    var doubleCheckRem by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var calendar by remember { mutableStateOf(Calendar.getInstance()) }
    var currentDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit)
    {
        while (true){
            delay(1000)
            currentDateTime = LocalDateTime.now()
        }
    }


    val month = currentDateTime.monthValue
    val day = currentDateTime.dayOfMonth
    var hour = currentDateTime.hour
    val minute = currentDateTime.minute

    val period = if (hour >= 12) "오후" else "오전"

    if (hour > 12) {
        hour -= 12
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val iconTint = if (isPressed) Color.DarkGray else Color.Red

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val allDays = setOf("일", "월", "화", "수", "목", "금", "토")
    val selectedDays = allDays.filterIndexed { index, _ -> alarm.alarmDayOfWeek[index] }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("requestCode", alarm.id)
    }

    val cancelPendingIntent = dismissAlarm.getCancelPendingIntent(alarm.id, context)
    val resetAlarmPendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_IMMUTABLE)

    if(selectedDays.isNotEmpty())
    {
        calendar = dismissAlarm.resetCalendar(alarm, selectedDays)
    }
    else
    {
        LaunchedEffect(Unit)
        {
            coroutineScope.launch(Dispatchers.IO) {
                saveSwitchUseCase.invoke(alarm.id, false)
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF212327)), contentAlignment = Alignment.Center)
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Text(
                text = "${month}월 ${day}일",
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                color = Color.White
            )

            Text(
                text = "$period ${hour}시 ${minute}분",
                modifier = Modifier.padding(16.dp),
                fontSize = 30.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = alarm.title,
                modifier = Modifier.padding(16.dp),
                fontSize = 25.sp,
                color = Color(0xFFFFE68E),
                fontFamily = FontFamily(Font(R.font.gwangwon_gyoyook_bold))
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(text = "알람 해제", color = Color.White, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(30.dp))

            IconButton(
                onClick =
                {
                    if (alarm.rem) {
                        coroutineScope.launch {
                            saveWakeUpTimeUseCase.invoke(LocalDateTime.now().withSecond(0).withNano(0))
                        }
                        doubleCheckRem = true
                    }
                    else
                    {
                        try
                        {
                            initAlarm(context, selectedDays, alarmManager, notificationManager, cancelPendingIntent, resetAlarmPendingIntent, alarm, calendar)
                        }
                        finally {
                            Handler(Looper.getMainLooper()).postDelayed({
                                val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                                activityManager.appTasks.forEach { it.finishAndRemoveTask() }
                                if (context is Activity) {
                                    context.finishAffinity()
                                }
                                exitProcess(0)
                            }, 200)
                        }
                    } },
                modifier = Modifier.size(110.dp),
                interactionSource = interactionSource
            ) {
                Icon(painter = painterResource(id = R.drawable.alarm_off_icon),
                    contentDescription = stringResource(id = R.string.description_alarm_off),
                    tint = iconTint)
            }
        }

    }


    if(doubleCheckRem) {
        AlertDialog(
            onDismissRequest = { doubleCheckRem = false },
            text = { Text("수면 기록을 체크했어요\n지금 일어나시는 건가요?") },
            confirmButton = {
                TextButton(onClick = {
                    try {
                        initAlarm(context, selectedDays, alarmManager, notificationManager, cancelPendingIntent, resetAlarmPendingIntent, alarm, calendar)
                        createRemNotification(context, alarm)
                    }
                    finally {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                            activityManager.appTasks.forEach { it.finishAndRemoveTask() }
                            if (context is Activity) {
                                context.finishAffinity()
                            }
                            exitProcess(0)
                        }, 200)
                    }
                }) {
                    Text("예")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    doubleCheckRem = false
                }) {
                    Text("아니오")
                }
            }
        )
    }
}

