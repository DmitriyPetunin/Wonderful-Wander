package com.example.presentation.usecase

interface DeletePostFromMySavedPostsUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}