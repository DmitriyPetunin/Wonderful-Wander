package com.example.feature.walk.api.action

sealed class WalkAction {

    data class UpdateCameraPermission(val isGranted: Boolean): WalkAction()
}