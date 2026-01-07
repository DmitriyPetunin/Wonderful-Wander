package com.example.feature.profile.api.state

data class UpdateProfileState(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val myPhotoVisibility: String = "PUBLIC",
    val savedPhotoVisibility:String = "PUBLIC",
    val walkVisibility: String = "PUBLIC",
)