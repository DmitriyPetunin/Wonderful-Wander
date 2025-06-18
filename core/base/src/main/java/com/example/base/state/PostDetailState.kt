package com.example.base.state

data class PostDetailState (
    val isLoading:Boolean = true,

    val postId:String = "",
    val photoUrl:String = "",
    val title:String = "",

    val categoryName:String = "",
    val user:UserData = UserData(),

    val likesCount:Int = 0,
    val commentsCount:Int = 0,
    val createdAt:String = ""

)


data class UserData(
    val userId: String = "",
    val username: String = "",
    val profilePictureUrl: String = ""
)