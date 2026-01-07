package com.example.domain.usecase

import com.example.domain.model.post.Comment


interface GetAllCommentsByPostIdUseCase {
    suspend fun invoke(postId:String,page:Int = 0,limit:Int = 10):Result<List<Comment>>
}