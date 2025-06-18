package com.example.base.model.post


data class PostResult (
    val postId:String,
    val title:String,
    val photoUrl:String,
    val categoryName:String,
    val user:UserDataResult,
    val likesCount:Int,
    val commentsCount:Int,
    val createdAt:String,
){
    companion object{
        val EMPTY = PostResult(
            postId = "",
            title = "",
            photoUrl = "",
            likesCount = 0,
            commentsCount = 0,
            user = UserDataResult.EMPTY,
            createdAt = "",
            categoryName = ""
        )
    }
}

data class UserDataResult(
    val userId:String,
    val userName:String,
    val avatarUrl:String,
){
    companion object{
        val EMPTY = UserDataResult(userId = "", userName = "", avatarUrl = "")
    }
}