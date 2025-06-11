package com.example.base.event


sealed interface GeoEvent {
    data object InteractionOne: GeoEvent
}