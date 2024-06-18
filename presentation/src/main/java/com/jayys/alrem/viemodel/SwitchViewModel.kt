package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.entity.AlarmEntity
import com.jayys.alrem.usecase.LoadSwitchUseCase
import com.jayys.alrem.usecase.SaveSwitchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun saveSwitchState(position: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        saveSwitchUseCase.invoke(position, isChecked)
        val updatedMap = _switchStates.value.toMutableMap().apply {
            this[position] = isChecked
        }
        _switchStates.value = updatedMap
    }

    fun loadSwitchStates(userList: List<AlarmEntity>) = viewModelScope.launch(Dispatchers.IO) {
        val states = mutableMapOf<Int, Boolean>()
        userList.forEach { user ->
            val isChecked = loadSwitchUseCase.invoke(user.id)
            states[user.id] = isChecked
        }
        _switchStates.value = states
    }

}