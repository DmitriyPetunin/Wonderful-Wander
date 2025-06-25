package com.example.base.model.post

data class Comment (
    val commentId: String,
    val text: String,
    val user: UserDataResult,
    val createdAt: String,
    val repliesCount: Int
){
    companion object{
        val EMPTY = Comment(
            commentId = "",
            text = "",
            user = UserDataResult.EMPTY,
            createdAt = "",
            repliesCount = -1
        )
    }
}