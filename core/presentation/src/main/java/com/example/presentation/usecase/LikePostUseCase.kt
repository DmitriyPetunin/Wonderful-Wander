package com.example.presentation.usecase

import com.example.base.model.post.LikeResult

interface LikePostUseCase {
    suspend fun invoke(postId:String):Result<LikeResult>
}