package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.LoadSwitchUseCase
import com.jayys.alrem.usecase.SaveSwitchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwitchViewModel @Inject constructor(
    private val saveSwitchUseCase: SaveSwitchUseCase,
    private val loadSwitchUseCase: LoadSwitchUseCase
) : ViewModel()
{
    private val _switchStates = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val switchStates: StateFlow<Map<Int, Boolean>> = _switchStates.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            _switchStates.value = loadSwitchUseCase.loadAll()
        }
    }

    private fun handleException(e: Exception, errorMessage: String) {
        _error.value = errorMessage
        e.printStackTrace()
    }


    fun saveSwitchState(position: Int, isChecked: Boolean) = viewModelScope.launch {
        try {
            saveSwitchUseCase.invoke(position, isChecked)
            val updatedMap = _switchStates.value.toMutableMap().apply {
                this[position] = isChecked
            }
            _switchStates.value = updatedMap
        } catch (e: Exception) {
            handleException(e, "스위치 상태를 저장하는 중 에러가 발생했습니다.")
        }
    }

    fun loadSwitchStates(alarmList: List<AlarmEntity>) = viewModelScope.launch {
        try {
            val states = loadSwitchUseCase.loadAll().toMutableMap()
            alarmList.forEach { alarm ->
                if (!states.containsKey(alarm.id)) {
                    states[alarm.id] = true
                }
            }
            _switchStates.value = states
        } catch (e: Exception) {
            handleException(e, "알람 목록을 불러오는 중 에러가 발생했습니다.")
        }
    }

}

