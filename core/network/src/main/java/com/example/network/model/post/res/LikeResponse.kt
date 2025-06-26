package com.example.network.model.post.res

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val likesCount:Int
)