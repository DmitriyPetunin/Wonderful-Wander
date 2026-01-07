package com.example.network.model.user.people

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeUserResponse (
    val statusCode:Int,
    val timestamp: String?,
    val error:String?,
    val message:String?
)