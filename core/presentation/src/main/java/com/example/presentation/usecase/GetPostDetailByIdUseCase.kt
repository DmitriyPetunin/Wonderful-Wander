package com.example.presentation.usecase

import com.example.base.model.post.PostResult

interface GetPostDetailByIdUseCase {
    suspend fun invoke(postId:String):Result<PostResult>
}