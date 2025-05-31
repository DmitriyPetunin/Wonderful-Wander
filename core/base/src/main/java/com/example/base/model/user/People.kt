package com.example.base.model.user

data class People (
    var userId: String,
    val avatarUrl: String,
    val username: String,
){
    companion object {
        val EMPTY = People(
            userId = "",
            avatarUrl = "",
            username = ""
        )
    }
}