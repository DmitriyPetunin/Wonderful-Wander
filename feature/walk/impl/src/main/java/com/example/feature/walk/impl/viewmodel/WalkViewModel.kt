package com.example.feature.walk.impl.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.feature.walk.api.action.WalkAction
import com.example.feature.walk.api.event.WalkEvent
import com.example.feature.walk.api.state.WalkState
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
):ViewModel() {

    init {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "walk_screen")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private val _state: MutableStateFlow<WalkState> = MutableStateFlow(WalkState())
    val state:StateFlow<WalkState> = _state


    private val _event: MutableSharedFlow<WalkEvent> = MutableSharedFlow<WalkEvent>()
    val event: SharedFlow<WalkEvent> = _event


    fun onAction(action: WalkAction){
        when(action){

            is WalkAction.UpdateCameraPermission -> {
                updateCameraPermissions(action.isGranted)
            }
            else -> {}
        }
    }


    private fun updateCameraPermissions(isGranted:Boolean){
        _state.update {
            it.copy(hasCameraPermission = isGranted)
        }
    }
}

