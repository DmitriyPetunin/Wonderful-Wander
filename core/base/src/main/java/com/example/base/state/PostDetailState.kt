package com.example.base.state

import com.example.base.model.post.Comment

data class PostDetailState (
    val isLoading:Boolean = true,

    val postId:String = "",
    val photoUrl:String = "",
    val title:String = "",

    val categoryName:String = "",
    val user:UserData = UserData(),

    val likesCount:Int = 0,
    val commentsCount:Int = 0,
    val createdAt:String = "",


    val listOfComments:List<CommentUi> = emptyList(),

    val isLoadingComments: Boolean = true,
    val endReached: Boolean = false,
    val isInitialLoadingComments: Boolean = true,
    val currentPage: Int = 1,
    val limit:Int = 10,

    val commentsIsVisible:Boolean = false

)


data class UserData(
    val userId: String = "",
    val username: String = "",
    val profilePictureUrl: String = ""
)