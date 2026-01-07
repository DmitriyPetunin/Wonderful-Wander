package com.example.network.model.post.res

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommentsResponse (
    @SerialName("data")
    val listOfComments:List<CommentResponse>?,
    val total:Int?,
    val limit:Int?,
    val offset:Int?,
)



@Serializable
data class CommentResponse (
    val commentId: String,
    val text: String,
    val user: UserResponse,
    val createdAt: String,
    val repliesCount: Int
)