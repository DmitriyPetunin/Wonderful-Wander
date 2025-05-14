package com.example.network.model.user.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)