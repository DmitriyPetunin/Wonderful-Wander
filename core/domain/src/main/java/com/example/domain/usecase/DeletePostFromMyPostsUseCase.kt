package com.example.domain.usecase

interface DeletePostFromMyPostsUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}