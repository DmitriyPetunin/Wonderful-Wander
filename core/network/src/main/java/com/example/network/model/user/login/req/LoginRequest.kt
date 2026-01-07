package com.example.network.model.user.login.req

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username:String,
    val password:String
)