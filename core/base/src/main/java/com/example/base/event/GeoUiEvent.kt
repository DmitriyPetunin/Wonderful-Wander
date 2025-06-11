package com.example.base.event


sealed interface GeoUiEvent {
    data object InteractionOne: GeoUiEvent
    data class InteractionTwo(val input:String): GeoUiEvent
}