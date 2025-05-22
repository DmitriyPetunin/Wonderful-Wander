package com.example.network.model.user.login.res

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val accessToken: String?,
    val refreshToken: String?
)