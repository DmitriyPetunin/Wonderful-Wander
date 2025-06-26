package com.example.network.model.post.req

import kotlinx.serialization.Serializable

@Serializable
class CreateCommentRequest(
    val text:String,
    val parentCommentId:String? = null
)