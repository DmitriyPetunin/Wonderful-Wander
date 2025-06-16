package com.example.network.model.photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UploadImageResponse(
    @SerialName("filename")
    val fileName: String,
)