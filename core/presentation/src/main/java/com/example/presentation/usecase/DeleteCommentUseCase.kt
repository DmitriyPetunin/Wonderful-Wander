package com.example.presentation.usecase

interface DeleteCommentUseCase {
    suspend fun invoke(postId:String,commentId:String):Result<Unit>
}