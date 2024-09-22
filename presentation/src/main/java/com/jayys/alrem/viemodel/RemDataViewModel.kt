package com.jayys.alrem.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.alrem.entity.RemEntity
import com.jayys.alrem.usecase.rem.AddRemUseCase
import com.jayys.alrem.usecase.rem.GetAllRemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemDataViewModel @Inject constructor(
    private val addRemUseCase: AddRemUseCase,
    private val getAllRemsUseCase: GetAllRemsUseCase
) : ViewModel() {

    private var _remData = MutableStateFlow<List<RemEntity>>(emptyList())
    val remData = _remData.asStateFlow()

    private val _error = MutableStateFlow("에러가 발생했습니다.")
    val error: StateFlow<String> = _error.asStateFlow()

    private fun handleException(e: Exception, errorMessage: String) {
        _error.value = errorMessage
        e.printStackTrace()
    }

    fun getAllRems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            getAllRemsUseCase.invoke().collect { rems ->
                _remData.value = rems
            }
        }
        catch (e: Exception){
            handleException(e, "수면 시간을 불러오는 중 에러가 발생했습니다.")
        }
    }

    fun addRem(rem : RemEntity) = viewModelScope.launch(Dispatchers.IO) {
        try {
            addRemUseCase.invoke(rem)
        }
        catch (e : Exception){
            handleException(e, "수면 시간을 기록하는 중 에러가 발생했습니다.")
        }

    }
}