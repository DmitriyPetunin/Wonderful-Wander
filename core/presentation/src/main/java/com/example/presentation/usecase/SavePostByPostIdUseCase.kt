package com.example.presentation.usecase

interface SavePostByPostIdUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}