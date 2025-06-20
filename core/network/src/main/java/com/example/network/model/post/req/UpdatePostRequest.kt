package com.example.network.model.post.req

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequest(
    val title:String,
    val imageFilename:String,
    val categoryId: Long,
)