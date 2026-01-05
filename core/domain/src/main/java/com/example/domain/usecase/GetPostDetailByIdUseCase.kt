package com.example.domain.usecase

import com.example.base.model.post.Post

interface GetPostDetailByIdUseCase {
    suspend fun invoke(postId:String):Result<Post>
}