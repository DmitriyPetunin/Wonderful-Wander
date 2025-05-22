package com.example.presentation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.base.action.WalkAction
import com.example.base.event.WalkEvent
import com.example.base.state.WalkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(

):ViewModel() {

    private val _state: MutableStateFlow<WalkState> = MutableStateFlow(WalkState())
    val state:StateFlow<WalkState> = _state


    private val _event: MutableSharedFlow<WalkEvent> = MutableSharedFlow<WalkEvent>()
    val event: SharedFlow<WalkEvent> = _event


    fun onAction(action: WalkAction){
        when(action){

            else -> {}
        }
    }
}