package com.example.feature.map.api.event


sealed interface GeoEvent {
    data object InteractionOne: GeoEvent
}