package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _events = MutableSharedFlow<MainEvent>()
    val events = _events.asSharedFlow()

    fun tryNavigate(featureName: String) {
        viewModelScope.launch {
            _events.emit(MainEvent.AccessGranted(input = featureName))
        }
    }

    fun accessDenied(featureName: String){
        viewModelScope.launch {
            _events.emit(MainEvent.AccessDenied(input = featureName))
        }
    }
}

sealed interface MainEvent{
    data class AccessDenied(val input:String):MainEvent
    data class AccessGranted(val input:String):MainEvent
}