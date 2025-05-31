package com.example.network.model.photo

import kotlinx.serialization.Serializable


@Serializable
data class UploadImageResponse(
    val photoId: String?,
)