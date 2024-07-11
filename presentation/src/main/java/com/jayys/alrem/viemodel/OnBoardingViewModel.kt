package com.jayys.alrem.viemodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.navigation.ScreenRoute
import com.jayys.alrem.usecase.datastore.LoadOnBoardingUseCase
import com.jayys.alrem.usecase.datastore.SaveOnBoardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    private val loadOnBoardingUseCase: LoadOnBoardingUseCase
) : ViewModel() {

    private val _onBoardingState = MutableStateFlow(true)
    val onBoardingState: StateFlow<Boolean> = _onBoardingState.asStateFlow()

    private val _startDestination = mutableStateOf<String?>(null)
    val startDestination = _startDestination

    init { loadOnBoardingState() }

    fun setOnBoardingState(state : Boolean) = viewModelScope.launch {
        saveOnBoardingUseCase.invoke(state)
        _onBoardingState.value = state
    }

    private fun loadOnBoardingState() = viewModelScope.launch {
        loadOnBoardingUseCase.invoke().collect{ state ->
            _startDestination.value = if (state)
            {
                ScreenRoute.MainScreen.route
            }
            else
            {
                ScreenRoute.OnBoardingScreen.route
            }
            _onBoardingState.value = false
        }
    }
}