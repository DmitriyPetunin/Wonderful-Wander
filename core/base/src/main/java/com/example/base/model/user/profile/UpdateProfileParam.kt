package com.example.base.model.user.profile

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility

class UpdateProfileParam (
    val email: String,
    val firstName: String,
    val lastName: String,
    val bio: String,
    val myPhotoVisibility: PhotosVisibility,
    val savedPhotoVisibility:PhotosVisibility,
    val walkVisibility: WalkVisibility
)