package com.jayys.alrem.screen.onboarding


import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.R
import com.jayys.alrem.permission.PermissionManager


@Composable
fun LastOnBoardingLayout(permissionManager: PermissionManager) {

    Box(modifier = Modifier.fillMaxSize().semantics { contentDescription = "권한 설정 온보딩 화면" })
    {
        BoxWithConstraints {
            val screenHeight = maxHeight
            val rowModifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.09f)
                .padding(horizontal = 35.dp)
                .padding(top = 5.dp)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))


                Row(modifier = rowModifier.clickable
                {
                    permissionManager.checkAndRequestNotificationPermissions()
                }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "알림", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    Icon(painter = painterResource(id = R.drawable.entry_icon),
                        contentDescription = stringResource(
                        id = R.string.description_entry_button
                    ), tint = Color.White)
                }
                Text(text = "앱이 알림을 보낼 수 있도록 합니다",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp))

                Spacer(modifier = Modifier.height(40.dp))


                Row(modifier = rowModifier.clickable
                {
                    permissionManager.checkAndRequestExactAlarmPermissions()
                }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "알람 및 리마인더", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    Icon(painter = painterResource(id = R.drawable.entry_icon),
                        contentDescription = stringResource(id = R.string.description_entry_button),
                        tint = Color.White)
                }
                Text(text = "정확한 시간의 알람을 실행하고 진동 기능을 사용하기 위해 필요합니다",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp))


                Spacer(modifier = Modifier.height(40.dp))


                Row(modifier = rowModifier.clickable
                {
                    permissionManager.checkAndRequestOverlayPermission()
                }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = "다른 앱 위에 표시", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    Icon(painter = painterResource(id = R.drawable.entry_icon),
                        contentDescription = stringResource(id = R.string.description_entry_button),
                        tint = Color.White)
                }
                Text(text = "알람이 울렸을 때 알람 화면이 보이도록 합니다",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp))

            }


        }
    }

}