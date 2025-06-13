package com.example.network.model.post.req


data class UpdatePostRequest(
    val title:String,
    val imageId:String,
    val categoryId: Long,
)