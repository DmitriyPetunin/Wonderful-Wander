package com.example.network.model.user.profile.req

import kotlinx.serialization.Serializable


@Serializable
data class UpdateProfileRequest(
    val email:String,
    val firstname:String,
    val lastname:String,
    val bio:String,
    val myPhotoVisibility: String,
    val savedPhotoVisibility:String,
    val walkVisibility: String
)