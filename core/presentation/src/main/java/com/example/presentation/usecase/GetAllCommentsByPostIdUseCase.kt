package com.example.presentation.usecase

import com.example.base.model.post.Comment

interface GetAllCommentsByPostIdUseCase {
    suspend fun invoke(postId:String,page:Int = 0,limit:Int = 10):Result<List<Comment>>
}