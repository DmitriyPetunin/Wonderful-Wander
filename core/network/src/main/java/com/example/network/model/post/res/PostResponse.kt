package com.example.network.model.post.res

import com.example.network.model.error.ExampleErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(
    @SerialName("data")
    val listOfPosts:List<PostResponse>?,
    val total:Int?,
    val limit:Int?,
    val offset:Int?,
    //val errorResponse: ExampleErrorResponse?
)

@Serializable
data class PostResponse(
    val postId:String,
    val title:String,
    @SerialName("imageUrl")
    val photoUrl:String,

    val category: CategoryResponse,
    val user: UserResponse,

    val likesCount:Int,
    val commentsCount:Int,
    val createdAt:String
)


@Serializable
data class CategoryResponse(
    val categoryId:Long,
    val name:String
)


@Serializable
data class UserResponse(
    val userId:String,
    @SerialName("username")
    val userName:String,
    @SerialName("avatarUrl")
    val avatarUrl:String?
)

