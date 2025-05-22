package com.example.base.model.user.friends

class Friend (
    val userId: String,
    val avatarUrl: String,
    val username: String,
){
    companion object {
        val EMPTY = Friend(
            userId = "",
            avatarUrl = "",
            username = ""
        )
    }
}