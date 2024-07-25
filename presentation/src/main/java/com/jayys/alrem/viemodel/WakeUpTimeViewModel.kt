package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.usecase.datastore.LoadWakeUpTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WakeUpTimeViewModel @Inject constructor(
    private val loadWakeUpTimeUseCase: LoadWakeUpTimeUseCase
) : ViewModel() {

    private val _wakeUpTime = MutableStateFlow(LocalDateTime.now())
    val wakeUpTime = _wakeUpTime.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init { loadWakeUpTime() }

    fun loadWakeUpTime() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val wakeUpTime = loadWakeUpTimeUseCase.invoke()
            _wakeUpTime.value = wakeUpTime
        }
        catch (e : Exception) {
            _error.value = "기상 시간을 불러오는 중 에러가 발생했습니다."
            e.printStackTrace()
        }

    }
}