package com.example.network.model.user.profile.req

import kotlinx.serialization.Serializable


@Serializable
data class UpdateProfileRequest(
    val email:String,
    val firstname:String,
    val lastname:String,
    val bio:String,
    val photoVisibility: String,
    val walkVisibility: String
)