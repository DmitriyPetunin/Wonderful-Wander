package com.example.network.model.walk.req

import kotlinx.serialization.Serializable

@Serializable
data class CreateWalkRequest (
    val name: String,
    val walkParticipants: List<String>,
    val startPointLongitude: Double,
    val startPointLatitude: Double
)