package com.example.presentation.usecase

interface DeletePostFromMyPostsUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}