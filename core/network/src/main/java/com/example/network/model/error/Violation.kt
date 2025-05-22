package com.example.network.model.error

import kotlinx.serialization.Serializable

@Serializable
data class Violation (
    val fieldName: String,
    val message: String
)