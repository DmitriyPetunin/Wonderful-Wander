package com.example.domain.usecase

import com.example.domain.model.post.LikeResult


interface LikePostUseCase {
    suspend fun invoke(postId:String):Result<LikeResult>
}