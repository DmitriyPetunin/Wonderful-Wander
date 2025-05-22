package com.example.network.model.error

import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
class ExampleErrorResponse (
    val timestamp: String?,
    val error: String?,
    val message: String
)