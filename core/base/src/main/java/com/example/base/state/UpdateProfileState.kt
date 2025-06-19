package com.example.base.state

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility

data class UpdateProfileState(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val myPhotoVisibility: String = "PUBLIC",
    val savedPhotoVisibility:String = "PUBLIC",
    val walkVisibility: String = "PUBLIC",
)