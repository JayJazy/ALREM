package com.jayys.alrem


import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.gson.Gson
import com.jayys.alrem.broadcastreceiver.AlarmReceiver
import com.jayys.alrem.entity.AlarmEntity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime


@AndroidEntryPoint
class DestinationAlarm : ComponentActivity() {

    private lateinit var windowInsetsController: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val alarmJson = intent.getStringExtra("alarm")
        val gson = Gson()
        val alarm: AlarmEntity = gson.fromJson(alarmJson, AlarmEntity::class.java)

        setContent {
            DestinationLayout(alarm)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DestinationLayout(alarm: AlarmEntity)
{
    val context = LocalContext.current
    var doubleCheckRem by remember { mutableStateOf(false) }

    val currentDateTime = LocalDateTime.now()

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

            Text(text = "알람 해제", color = Color.White, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(30.dp))

            IconButton(
                onClick =
                {
                    if (alarm.rem) {
                        doubleCheckRem = true
                    }
                    else {
                        val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
                            putExtra("requestCode", alarm.id)
                            putExtra("action", "dismiss")
                    }
                    context.sendBroadcast(dismissIntent)
                } },
                modifier = Modifier.size(110.dp),
                interactionSource = interactionSource
            ) {
                Icon(painter = painterResource(id = R.drawable.alarm_off_icon),
                    contentDescription = "alarm_off",
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
                    val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
                        putExtra("requestCode", alarm.id)
                        putExtra("action", "dismiss")
                    }
                    context.sendBroadcast(dismissIntent)
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
