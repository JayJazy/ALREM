package com.jayys.alrem.component

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.viemodel.OnBoardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingButton(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    text: String,
    permissionManager : PermissionManager,
    onFinish: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel
)
{
    val context = LocalContext.current
    val buttonColor = if(text == "시작하기") Color.White else Color(0xFF72CCFF)
    val textColor = if(text == "시작하기") Color(0xFF51C0FF) else Color.White

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(horizontal = 40.dp).padding(vertical = 5.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(buttonColor)
        .clickable {
            if(text == "시작하기")
            {
                if(checkPermissions(permissionManager)){
                    onBoardingViewModel.setOnBoardingState(true)
                    onFinish()
                }
                else
                {
                    Toast.makeText(context, "권한을 모두 설정해 주세요", Toast.LENGTH_LONG).show()
                }
            }
            else{
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }, contentAlignment = Alignment.Center)
    {
        Text(text = text, color = textColor, style = MaterialTheme.typography.titleSmall, fontSize = 18.sp)
    }
}

fun checkPermissions(permissionManager: PermissionManager): Boolean {

    return permissionManager.checkAndRequestExactAlarmPermissions() &&
            permissionManager.checkAndRequestNotificationPermissions() &&
            permissionManager.checkAndRequestOverlayPermission() &&
            permissionManager.checkAndRequestNotificationServicePermissions()
}