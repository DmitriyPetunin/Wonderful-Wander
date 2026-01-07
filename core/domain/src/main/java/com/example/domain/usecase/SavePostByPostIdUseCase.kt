package com.example.domain.usecase

interface SavePostByPostIdUseCase {
    suspend fun invoke(postId:String):Result<Unit>
}