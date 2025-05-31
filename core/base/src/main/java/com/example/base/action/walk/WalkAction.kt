package com.example.base.action.walk

sealed class WalkAction {

    data class UpdateCameraPermission(val isGranted: Boolean): WalkAction()
}