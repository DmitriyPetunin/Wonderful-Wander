package com.example.base.action

sealed class GeoAction {
    data class UpdateCurrentCenter(val latitude: Double, val longitude: Double) : GeoAction()
}