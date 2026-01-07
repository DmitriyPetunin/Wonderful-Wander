package com.example.feature.map.api.action

sealed class MapAction {
    data class UpdateCurrentCenter(val latitude: Double, val longitude: Double) : MapAction()
    data object UpdateText: MapAction()
    data object NavigateToCreateWalkPage:MapAction()
}