package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.usecase.LoadSleepOfTimeUseCase
import com.jayys.alrem.usecase.SaveSleepOfTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TimeOfSleepViewModel @Inject constructor(
    private val saveSleepOfTimeUseCase: SaveSleepOfTimeUseCase,
    private val loadSleepOfTimeUseCase: LoadSleepOfTimeUseCase
) : ViewModel() {

    private val _timeOfSleep = MutableStateFlow(false)
    val timeOfSleep: StateFlow<Boolean> = _timeOfSleep.asStateFlow()

    init { loadTimeOfSleep() }

    fun setTimeOfSleep(value: Boolean) {
        viewModelScope.launch {
            saveSleepOfTimeUseCase(value)
        }
    }

    private fun loadTimeOfSleep() = viewModelScope.launch {
        loadSleepOfTimeUseCase().collect { value ->
            _timeOfSleep.value = value
        }
    }
}