package com.example.base.event


sealed interface GeoUiAction {
    data object InteractionOne: GeoUiAction
    data class InteractionTwo(val input:String): GeoUiAction
}