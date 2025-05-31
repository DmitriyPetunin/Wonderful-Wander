package com.example.base.action.geo

sealed class GeoAction {
    data class UpdateCurrentCenter(val latitude: Double, val longitude: Double) : GeoAction()
    data object UpdateText: GeoAction()
}