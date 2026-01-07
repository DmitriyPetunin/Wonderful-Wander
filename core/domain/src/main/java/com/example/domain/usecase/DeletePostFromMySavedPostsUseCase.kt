package com.example.domain.usecase

interface DeletePostFromMySavedPostsUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}