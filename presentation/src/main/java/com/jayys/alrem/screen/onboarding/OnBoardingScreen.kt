package com.jayys.alrem.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jayys.alrem.component.OnBoardingButton
import com.jayys.alrem.permission.PermissionManager
import com.jayys.alrem.viemodel.OnBoardingViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    permissionManager: PermissionManager,
    onFinish: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel())
{

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { 3 }

    Column(modifier = Modifier
        .fillMaxSize().semantics { contentDescription = "온보딩 화면" }
        .background(MaterialTheme.colorScheme.background)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            when(pageIndex)
            {
                0 -> FirstOnBoardingLayout()
                1 -> SecondOnBoardingLayout()
                else -> LastOnBoardingLayout(permissionManager)
            }
        }

        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (pagerState.currentPage < 2) {
                OnBoardingButton(pagerState, coroutineScope, "다 음",  permissionManager, onFinish, onBoardingViewModel)
            }
            else {
                OnBoardingButton(pagerState, coroutineScope, "시작하기",  permissionManager, onFinish, onBoardingViewModel)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}